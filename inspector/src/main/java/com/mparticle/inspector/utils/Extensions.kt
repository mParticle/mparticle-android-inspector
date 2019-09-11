package com.mparticle.inspector.utils

import android.view.View
import android.widget.TextView
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.Constants
import com.mparticle.inspector.Inspector
import com.mparticle.internal.InternalSession
import com.mparticle.shared.User
import com.mparticle.shared.events.*
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun StackTraceElement.getApiName(): String {
    val classNameString = getClassName(className, methodName)
    val apiNameBuilder = StringBuilder()
    apiNameBuilder.append(classNameString)
    apiNameBuilder.append(".")
    apiNameBuilder.append(methodName)
    apiNameBuilder.append("()")
    return apiNameBuilder.toString()
}

fun getClassName(packageName: String, methodName: String): String {
    val packageNames = packageName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val className = packageNames[packageNames.size - 1]
    if (className.isObfuscated()) {
        try {
            val superClasses = Class.forName(packageName).let {
                ArrayList<Class<in Nothing>>().apply {
                    it.superclass?.also { add(it) }
                    addAll(it.interfaces)
                    Unit
                }
            }
            superClasses.forEach { superClass ->
                if (superClass.methods.any { it.name == methodName }) {
                    val superClassName = getClassName(superClass.name, methodName)
                    if (!superClassName.isObfuscated()) {
                        return superClassName
                    }
                }
            }
        } catch (_: Exception) { }
    }
    return className
}

fun String.isObfuscated(): Boolean {
    return this[0].isLowerCase() && length < 3
}

fun StackTraceElement.isExternalApiInvocation(): Boolean {
    return !className.startsWith("com.mparticle") ||
            className == "com.mparticle.internal.KitFrameworkWrapper" ||
            isClientMethod()
}

fun StackTraceElement.isClientMethod(): Boolean {
    return Inspector.getInstance()?.application?.packageName?.let { className.startsWith(it) } ?: false
}

fun String.apiMapName(threadName: String?): String {
    return this + "_" + (threadName ?: "none")
}

fun <T> Array<T>.getFirstLastOf(startingAt: Int = 0, test: (T) -> Boolean): Int {
    var apiCallIndex = 0
    var found = false
    for (element in this) {
        if (apiCallIndex >= startingAt) {
            val inClass = test(element)
            if (found && !inClass) {
                break
            }
            found = inClass
        }
        apiCallIndex++
    }
    return apiCallIndex - 1
}

fun <T> Array<T>.indexOfFirst(startingAt: Int, test: (T) -> Boolean): Int {
    forEachIndexed{ index: Int, t: T ->
        if (index >= startingAt) {
            if (test(t)) {
                return index
            }
        }
    }
    return -1
}

fun Any.isPrimitiveOrString(): Boolean {
    return when (this) {
        is Double,
        is Float,
        is Int,
        is Long,
        is Byte,
        is Short,
        is Char,
        is Boolean,
        is String,
        javaClass.isPrimitive -> true
        else -> false
    }
}

//this could be a primitive or Map<Sting, Any>, depending on whether it is primitive or an object.
fun Any.toObjectArgument(id: Int? = null): ObjectArgument {
    val obj = this
    return when {
        this.isPrimitiveOrString() -> ObjectArgument(this::class.java.name, obj.toPrimitive())
        this is Enum<*> -> ObjectArgument(this::class.java.name, EnumObject(name))
        this is List<*> -> ObjectArgument(this::class.java.name, toCollectionObject())
        this is Map<*, *> -> ObjectArgument(this::class.java.name, toMapObject())
        else -> ObjectArgument(this::class.java.name, obj.toObj())
    }
}

fun Any.toPrimitive() = Primitive(this)

fun Field.toField(value: Any?): FieldObject {
    val accessLevel = when {
        Modifier.isPrivate(modifiers) -> FieldObject.PRIVATE
        Modifier.isProtected(modifiers) -> FieldObject.PROTECTED
        Modifier.isPublic(modifiers) -> FieldObject.PUBLIC
        else -> FieldObject.PACKAGE_PRIVATE
    }
    return FieldObject(accessLevel, name, value?.toObjectArgument(), false)
}

fun Method.toField(result: Any?): FieldObject {
    val accessLevel = when {
        Modifier.isPrivate(modifiers) -> FieldObject.PRIVATE
        Modifier.isProtected(modifiers) -> FieldObject.PROTECTED
        Modifier.isPublic(modifiers) -> FieldObject.PUBLIC
        else -> FieldObject.PACKAGE_PRIVATE
    }
    return FieldObject(accessLevel, name, result?.toObjectArgument(), true)
}

fun List<*>.toCollectionObject(): CollectionObject {
    return map {
       it?.toObjectArgument()
    }.let {
        CollectionObject(it)
    }
}

fun Map<*, *>.toMapObject(): MapObject {
    return entries.associate { (key, value) ->
        key?.toObjectArgument() to value?.toObjectArgument()
    }.let {
        MapObject(it)
    }
}

fun Any.toObj(): Obj {
    val obj = this
    val fields = ArrayList<FieldObject>()
    javaClass.declaredFields
            .filter { !(Modifier.isFinal(it.modifiers)) && it.declaringClass.name.startsWith("com.mparticle") }
            .map { field ->
                field.isAccessible = true
                if (field.isAccessible) {
                    val fieldValue = field.get(obj)
                    field.toField(fieldValue)
                } else {
                    null
                }
            }
            .forEach { fieldObject ->
                fieldObject?.let { fields.add(it) }
            }
    javaClass.methods
            .filter { it.isRelevant() }
            .map { method ->
                val methodName = method.name.run {
                    val name = replace("get", "")
                    "${name.substring(0, 1).toLowerCase()}${name.substring(1)}" +
                            ""
                }
                val result = method.invoke(obj)
                method.toField(result)
            }
            .forEach { fields.add(it) }
    return Obj(fields)
}

fun Method.isRelevant(): Boolean {
    return !Modifier.isPrivate(modifiers) &&
            parameterTypes.size == 0 &&
            returnType != java.lang.Void.TYPE &&
            name.startsWith("get") &&
            declaringClass.name.startsWith("com.mparticle")
}

/**
 * returns the ObjectArgument in either a Primitive/String value, if it
 * represents a primitive or String, or a Map<String, Any> if it represents
 * an MParticle object
 *
 * @param cleanMethodsOnly should getter names be normalied, i.e "getId" -> "id" when true
 */
fun ObjectArgument.toMapOrValue(cleanMethodsOnly: Boolean = true): Any {
    return when (value) {
        is Primitive -> (value as Primitive).value
        is com.mparticle.shared.events.EnumObject -> (value as com.mparticle.shared.events.EnumObject).name
        is Obj -> {
            val obj = value as Obj
            val objectMap = HashMap<String, Any?>()
            obj.fields
                    .filter {
                        !cleanMethodsOnly || it.isMethod
                    }
                    .forEach {
                        var fieldName = it.fieldName
                        if (cleanMethodsOnly) {
                            if (fieldName.startsWith("get")) {
                                fieldName = fieldName.replace("get", "")
                                fieldName = "${fieldName.substring(0, 1).toLowerCase()}${fieldName.substring(1)}"
                            }
                        }
                        objectMap.put(fieldName, it.objectArgument?.toMapOrValue(cleanMethodsOnly))
                    }
            objectMap
        }
        is CollectionObject -> (value as CollectionObject).values.map {
            it?.toMapOrValue()
        }
        is MapObject -> (value as MapObject).valuesMap.entries.associate { (key, value) ->
            key?.toMapOrValue() to value?.toMapOrValue()
        }
    }
}



fun View.setChainClickable(id: Int?, displayCallback: ((Int) -> Unit)?) {
    background = android.support.v4.content.ContextCompat.getDrawable(context, android.R.color.holo_orange_light)?.apply {
        alpha = 100
    }
    setOnClickListener {
        if (id != null) {
            displayCallback?.invoke(id)
        }
    }
}

fun Long.milliToSecondsString(): String {
    return "${this / 1000}.${this % 1000 / 100}s"
}

fun <T> MutableList<WeakReference<T>>.broadcast(broadcast: (T) -> Unit) {
    removeAll { it -> it.get() == null }
    toList()
            .forEach { it -> it.get()?.let { value -> broadcast(value)} }
}

fun View.visible(visible: Boolean) {
    val newVisibility = if (visible) View.VISIBLE else View.GONE
    if (newVisibility != visibility) {
        visibility = newVisibility
    }
}

fun TextView.expanded(expanded: Boolean) {
    if (expanded) {
        setSingleLine(false)
    } else {
        setSingleLine(true)
    }
}

fun TextView.setJsonHandling(key: String, expanded: Boolean, json: String?) {
    when (json?.length) {
        null, in 0..4 -> {
            text = "$key = {}"
        }
        else -> {
            if (expanded) {
                text = "$key =      -"
            } else {
                text = "$key = {... +"
            }
        }
    }
}

fun JSONObject.toMap(): Map<String, Any> {
    val map = HashMap<String, Any>()
    keys().forEach {
        val value = get(it)
        when(value) {
            is JSONObject -> map.put(it, value.toMap())
            else -> map.put(it, value)
        }
    }
    return map
}

fun MParticleUser.wrapper(): User {
    return User(id,
            userAttributes,
            userIdentities.entries.associate { it.key.name to it.value },
            cart.toObjectArgument() as Map<String, Any?>,
            consentState.toObjectArgument() as Map<String, Any?>,
            isLoggedIn,
            firstSeenTime,
            lastSeenTime)
}

fun Map<*, *>.json(): JSONObject {
    val json = JSONObject()
    entries.forEach {
        when(it.value) {
            is Map<*, *> -> json.put(it.key.toString(), (it.value as Map<*, *>).json())
            else -> json.put(it.key.toString(), it.value)
        }
    }
    return json
}

fun InternalSession.getMap(): Map<String, Any> {
    return HashMap<String, Any>().apply {
        put(Constants.SESSION_ID, mSessionID)

        put("foreground time; ", {
                (System.currentTimeMillis() - mSessionStartTime - backgroundTime).milliToSecondsString()
            })
        put("background time: ", { backgroundTime?.milliToSecondsString() ?: "-" })
        put("event count: ", { mEventCount.toString() ?: "-" })
        put("mpids: ", {
            mpids?.run {
                if (size == 0) {
                    "-"
                } else {
                    joinToString { it.toString() }
                }
            } ?: "-"
        })
        put("length: ", {
            run {
                (mLastEventTime - mSessionStartTime).milliToSecondsString()
            } ?: "-"
        })
    }
}
package com.mparticle.inspector.utils

import android.view.View
import android.widget.TextView
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.Inspector
import com.mparticle.inspector.UserWrapper
import com.mparticle.shared.User
import org.json.JSONArray
import org.json.JSONObject
import java.lang.ref.WeakReference
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

fun Any.printClass(): Any {
    val obj = this
    return when {
        this.isPrimitiveOrString() -> this
        else -> {
            javaClass.fields.fold(JSONObject()) { acc, field ->
                if (Modifier.isPublic(field.modifiers) &&
                        !Modifier.isFinal(field.modifiers) &&
                        field.declaringClass.name.startsWith("com.mparticle")) {
                    var value = field.get(obj)
                    when (value?.javaClass?.isPrimitiveOrString()) {
                        true -> acc.put(field.name, value)
                        false -> value.printClass(field.name, acc)
                    }
                }
                acc
            }.let {
                javaClass.methods.fold(it) { acc, method ->
                    if (method.isRelevant()) {
                        val value = method.invoke(obj)

                        val methodName = method.name.run {
                            val name = replace("get", "")
                            "${name.substring(0, 1).toLowerCase()}${name.substring(1)}" +
                                    ""
                        }
                        when (value?.isPrimitiveOrString()) {
                            true -> acc.apply { put(methodName, value) }
                            false -> value.printClass(methodName, acc)
                            null -> acc
                        }
                    } else {
                        acc
                    }
                }
            }
        }
    }
}

fun Any?.printClass(key: String, jsonObject: JSONObject): JSONObject {
    when (this) {
        null -> jsonObject.put(key, JSONObject.NULL)
        isPrimitiveOrString() -> jsonObject.put(key, this)
        is Map<*, *> -> {
            entries.sortedBy { it.key.toString() }
                    .fold(JSONObject()) { acc, entry ->
                        entry.value?.printClass(entry.key.toString(), acc) ?: acc
                    }
                    .let { jsonObject.put(key, it) }
        }
        is List<*> -> {
            this.sortedBy { it.toString() }
                    .fold(JSONArray()) { acc, t: Any? ->
                        acc.apply { put(t?.printClass()) }
                    }
                    .let { jsonObject.put(key, it) }
        }
        is Enum<*> -> jsonObject.put(key, this.name)
        else ->
            if (this.javaClass.`package`?.name?.startsWith("com.mparticle") ?: false) {
                jsonObject.put(key, printClass())
            } else {
                jsonObject.put(key, this.toString())
            }
    }
    return jsonObject
}

fun Method.isRelevant(): Boolean {
    return java.lang.reflect.Modifier.isPublic(modifiers) &&
            parameterTypes.size == 0 &&
            returnType != java.lang.Void.TYPE &&
            name.startsWith("get") &&
            declaringClass.name.startsWith("com.mparticle")
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
    return UserWrapper(this)
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
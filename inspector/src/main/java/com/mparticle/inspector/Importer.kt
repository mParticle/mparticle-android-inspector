package com.mparticle.inspector

import android.content.Context
import android.content.ContextWrapper
import com.mparticle.MParticle
import com.mparticle.commerce.Cart
import com.mparticle.identity.IdentityApi
import com.mparticle.identity.IdentityStateListener
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.extensions.toPrimitive
import com.mparticle.internal.Logger
import com.mparticle.shared.Serializer
import com.mparticle.shared.events.*
import java.lang.reflect.Modifier

class Importer(val context: Context) {

    fun deserializeEvents(eventString: String): List<Event> = Serializer().deserialize(eventString)

    fun executeApiEvents(apiEvents: List<ApiCall>) {
            apiEvents.forEach { it.execute() }
    }

    fun ApiCall.execute(): Boolean {
        return when (getClassName()) {
            MParticle::class.simpleName -> {
                val instance = MParticle.getInstance()
                MParticle::class.java.invokeMethod(instance, this)
                true
            }
            IdentityApi::class.simpleName -> {
                val instance = MParticle.getInstance()?.Identity()
                IdentityApi::class.java.invokeMethod(instance, this)
                true
            }
            Cart::class.simpleName -> {
                val identityApi = MParticle.getInstance()?.Identity()
                val mpid = objectArgument?.getMpid()
                val user = mpid?.let { identityApi?.getUser(it) } ?: identityApi?.currentUser
                val instance = user?.cart
                Cart::class.java.invokeMethod(instance, this)
                true
            }
            MParticleUser::class.java.simpleName -> {
                val identityApi = MParticle.getInstance()?.Identity()
                val mpid = objectArgument?.getMpid()
                val instance = mpid?.let { identityApi?.getUser(it)} ?: identityApi?.currentUser
                MParticleUser::class.java.invokeMethod(instance, this)
                true
            }
            else -> false
        }
    }

    private fun <T>Class<T>.invokeMethod(instance: T?, apiEvent: ApiCall) {
        methods.filter { it.name == apiEvent.getMethodName() }
                .forEach { method ->
                    if (method.parameterTypes.size == apiEvent.arguments.size) {
                        val arguments = apiEvent.arguments.map { it.toObject() }
                        try {
                            when (arguments.size) {
                                0 -> method.invoke(instance)
                                else -> method.invoke(instance, *arguments.toTypedArray())
                            }
                        } catch (e: java.lang.Exception) {
                            Logger.error(e)
                        }
                    }
                }
    }

    fun ObjectArgument.toObject(): Any? {
        val v = value
        return when (v) {
            is Primitive -> v.value
            is EnumObject -> v.toEnum(fullClassName)
            is Obj -> v.toObject(fullClassName)
            is CollectionObject -> v.values.map { it.toObject() }
            is MapObject -> v.valuesMap.entries.associate { (k, v) ->
                k.toObject() to v.toObject()
            }
            is NullObject -> null
        }
    }

    fun ObjectArgument.getMpid(): Long? {
        when (className) {
            Cart::class.java.name ->
                return (value as? Obj)?.run {
                    fields
                            .firstOrNull { !it.isMethod && it.fieldName == "userId" }
                            .let {
                                (it?.objectArgument?.value?.toPrimitive()?.value as? Long)
                            }
                }
            MParticleUser::class.java.name ->
                return (value as? Obj)?.run {
                    fields
                            .firstOrNull { it.isMethod && it.fieldName.startsWith("getId") }
                            .let {
                                (it?.objectArgument?.value?.toPrimitive()?.value as? Long)
                            }
                }
            else -> return null
        }
    }

    fun Obj.toObject(className: String): Any? {
        var clazz: Class<*>?
        val instance = if (className.endsWith("TestAppApplication")) {
            clazz = IdentityStateListener::class.java
            TestClass()
        } else {
            clazz = Class.forName(className)
            clazz.instantiate()
        }
        fields
                .filterNot { it.isMethod }
                .forEach { field ->
                    clazz?.declaredFields
                            ?.firstOrNull { it.name == field.fieldName }
                            .let {
                                it?.isAccessible = true
                                it?.set(instance, field.objectArgument?.toObject())
                            }
                }
        fields
                .filter { it.isMethod }
                .forEach { method ->
                    clazz?.methods
                            ?.firstOrNull {
                                it.name == method.fieldName &&
                                        it.parameterTypes.size == 0 &&
                                        Modifier.isPublic(it.modifiers)
                            }
                            .let {
                                val returnValue = it?.invoke(instance)
                                val expected = method.objectArgument?.toObject()
                                if (returnValue != expected) {
                                    //assertEquals(method.objectArgument?.toObject(), it?.invoke(instance))
                                    Logger.error("unequal values for class: ${instance::class.java.name}, method: ${method.fieldName}")
                                }
                            }
                }
        return instance
    }

    fun EnumObject.toEnum(className: String): Enum<*> {
        val constructor = Class.forName(className).methods.first { it.name == "valueOf" }
        return constructor.invoke(null, name) as Enum<*>
    }

    private fun Class<*>.instantiate(): Any {
        if (isPrimitive) {
            return when (this) {
                Int::class.java -> 0
                Double::class.java -> 0.0
                Float::class.java -> 0f
                Long::class.java -> 0L
                String::class.java -> ""
                Boolean::class.java -> false
                else -> throw RuntimeException("unknown type: $this.name")
            }
        }
        var instance: Any? = null
        declaredConstructors
                .sortedBy { it.parameterTypes.size }
                .forEach {
                    if (instance == null) {
                        try {
                            it.isAccessible = true
                            instance = it.newInstance(*it.parameterTypes.map { it.instantiate() }.toTypedArray())
                        } catch (_: Exception) {
                            Logger.error("couldn't start ${it.parameterTypes.joinToString { it.name }}")
                        }
                    }
                }
        if (instance == null) {
            Logger.error("instance can't be instantiated")
        }
        return instance!!

    }

    inner class TestClass: ContextWrapper(context), IdentityStateListener {
        override fun onUserIdentified(p0: MParticleUser, p1: MParticleUser?) {

        }

    }
}


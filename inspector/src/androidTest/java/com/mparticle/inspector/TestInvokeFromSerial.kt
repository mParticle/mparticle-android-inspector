package com.mparticle.inpector

import android.content.Context
import android.content.ContextWrapper
import android.os.Looper
import android.support.test.InstrumentationRegistry
import com.mparticle.MParticle
import com.mparticle.identity.IdentityApi
import com.mparticle.identity.IdentityStateListener
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.BaseAbstractTest
import com.mparticle.inspector.test.R
import com.mparticle.inspector.utils.toObj
import com.mparticle.inspector.utils.toObjectArgument
import com.mparticle.internal.Logger
import com.mparticle.shared.Serializer
import com.mparticle.shared.events.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.lang.reflect.Modifier
import java.util.*

class TestInvokeFromSerial: BaseAbstractTest() {
    val serializer = Serializer()

    @Before
    fun beforeTest() {
        Looper.prepare()
    }


    @Test
    fun testTest() {
//        val fileContents = File("sample.txt").readText()
        val fileContents = mContext.resources.openRawResource(R.raw.sample).bufferedReader().readLines().joinToString { it }
        val events = serializer.deserialize(fileContents)

//        val startTime = events.minBy { it.createdTime }?.createdTime!!

        val apiEvents = events.filter { it is ApiCall && !(it is KitApiCall)} as List<ApiCall>
//        val stateAllUsers = events.firstOrNull { it is StateAllUsers } as? StateAllUsers

        apiEvents.forEach { apiEvent ->
            val split = apiEvent.endpoint.split(".")
            val className = split[0]
            val methodName = split[1].replace("()", "")
            when (className) {
                MParticle::class.simpleName -> {
                    val instance = MParticle.getInstance()
                    MParticle::class.java.invokeMethod(instance, methodName, apiEvent)
                }
                IdentityApi::class.simpleName -> {
                    val instance = MParticle.getInstance()?.Identity()
                    IdentityApi::class.java.invokeMethod(instance, methodName, apiEvent)
                }
            }
        }

        when (apiEvents) {

        }

        assertEquals(37, events.size)

    }
}

fun List<ObjectArgument>.toObjects(): List<Any?> {
    return map {
        it.toObject()
    }
}

fun ObjectArgument.toObject(): Any? {
    val v = value
    return when (v) {
        is Primitive -> v.value
        is EnumObject -> v.toEnum(fullClassName)
        is Obj -> v.toObject(fullClassName)
        is CollectionObject -> v.values.map { it?.toObject() }
        is MapObject -> v.valuesMap.entries.associate { (k, v) ->
            k?.toObject() to v?.toObject()
        }
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
            .forEach {field ->
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
                                    it.parameterCount == 0 &&
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

//fun ObjectArgument.toObj(): Any? {
//    val context = InstrumentationRegistry.getContext()
//    val fieldMap = HashMap(value as Map<String, Any>)
//    return when (fullClassName) {
//        MParticleOptions::class.java.name -> {
//            val builderMethods = MParticleOptions.Builder::class.java.methods
//            val builder = MParticleOptions.builder(context)
//                    .credentials(fieldMap.remove("apiKey") as String, fieldMap.remove("apiSecret") as String)
//            fieldMap.entries.forEach { (paramName, paramValue) ->
//                val method = builderMethods.firstOrNull { it.name == paramName }
//                method?.parameters?.get(0)?.let { param ->
//                    when {
//                        param.type.isEnum == true -> {
//                            val objectArgument = paramValue as ObjectArgument
//                            val constructor = Class.forName(objectArgument.fullClassName).methods.first { it.name == "valueOf" }
//                            val enumInstance = constructor.invoke(null, paramValue.value as String)
//                            method.invoke(builder, enumInstance)
//                        }
//                        param.type.isPrimitiveOrString() || param.type.isPrimitive -> method.invoke(builder, paramValue)
//                        else -> method.invoke(builder, (paramValue as ObjectArgument).toObj())
//                    }
//                }
//            }
//            builder.build()
//        }
//        NetworkOptions::class.java.name -> {
//            val builder = NetworkOptions.builder()
//            fieldMap.remove("pinningDisabledInDevelopment")?.let { pinningDisabledInDevelopment ->
//                builder.setPinningDisabledInDevelopment((pinningDisabledInDevelopment as String).toBoolean())
//            }
//            val domainMappings = fieldMap.entries.filter { it.key != "domainMappings" }
//                    .map { domainMapping ->
//                        val domainValue = (domainMapping.value as ObjectArgument).value as HashMap<String, Any>
//                        val methodName = when (domainMapping.key) {
//                            "eventsDomain" -> "eventsMapping"
//                            "configDomain" -> "configMapping"
//                            "identityDomain" -> "identityMapping"
//                            "audienceDomain" -> "audienceMapping"
//                            else -> null
//                        }
//                        val method = DomainMapping::class.java.methods.firstOrNull { it.name == methodName }
//                        val domainMappingObj = method?.invoke(null, domainValue["url"]) as DomainMapping.Builder
//                        (domainValue["certificates"] as List<Map<String, String>>).forEach {
//                            Certificate.with(it["alias"]!!, it["certificate"]!!)?.let { domainMappingObj.addCertificate(it) }
//                        }
//                        domainMappingObj.build()
//                    }
//            builder.setDomainMappings(domainMappings)
//            builder.build()
//        }
//        IdentityApiRequest::class.java.name -> {
//            val builder = if (fieldMap.contains("mpid")) {
//                //TODO
//                //how is this going to work?
//                //we need a step before all this to set up the SDK state, probably by consuming the AllUsers (?)event
//                IdentityApiRequest.withUser(MParticle.getInstance()?.Identity()?.getUser(fieldMap.remove("mpid") as Long))
//            } else {
//                IdentityApiRequest.withEmptyUser()
//            }
//        }
//        else -> null
//    }
//}

fun <T>Class<T>.invokeMethod(instance:T?, methodName: String, apiEvent: ApiCall) {
    methods.filter { it.name == methodName }
            .forEach { method ->
                if (method.parameters.size == apiEvent.objectArguments?.size) {
                    val arguments = apiEvent.objectArguments?.toObjects()
                    try {
                        when (arguments?.size) {
                            0, null -> method.invoke(instance)
                            else -> method.invoke(instance, *arguments.toTypedArray())
                        }
                    } catch (e: java.lang.Exception) {
                        Logger.error(e)
                    }
                }
            }
}


fun Class<*>.instantiate(): Any {
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
            .sortedBy { it.parameterCount }
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


class TestClass: ContextWrapper(InstrumentationRegistry.getContext()), IdentityStateListener {
    override fun onUserIdentified(p0: MParticleUser, p1: MParticleUser?) {

    }

}
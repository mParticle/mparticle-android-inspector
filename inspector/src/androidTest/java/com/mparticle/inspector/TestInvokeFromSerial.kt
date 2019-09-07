package com.mparticle.inpector

import android.os.Looper
import android.support.test.InstrumentationRegistry
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.mparticle.identity.IdentityApi
import com.mparticle.identity.IdentityApiRequest
import com.mparticle.inspector.BaseAbstractTest
import com.mparticle.inspector.test.R
import com.mparticle.inspector.utils.isPrimitiveOrString
import com.mparticle.networking.Certificate
import com.mparticle.networking.DomainMapping
import com.mparticle.networking.NetworkOptions
import com.mparticle.shared.Serializer
import com.mparticle.shared.events.ApiCall
import com.mparticle.shared.events.KitApiCall
import com.mparticle.shared.events.ObjectArgument
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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
        val apiEvents = events.filter { it is ApiCall && !(it is KitApiCall)} as List<ApiCall>


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
    val context = InstrumentationRegistry.getContext()
    val fieldMap = HashMap(value as Map<String, Any>)
    return when (fullClassName) {
        MParticleOptions::class.java.name -> {
            val builderMethods = MParticleOptions.Builder::class.java.methods
            val builder = MParticleOptions.builder(context)
                    .credentials(fieldMap.remove("apiKey") as String, fieldMap.remove("apiSecret") as String)
            fieldMap.entries.forEach { (paramName, paramValue) ->
                val method = builderMethods.firstOrNull { it.name == paramName }
                method?.parameters?.get(0)?.let { param ->
                    when {
                        param.type.isEnum == true -> {
                            val objectArgument = paramValue as ObjectArgument
                            val constructor = Class.forName(objectArgument.fullClassName).methods.first { it.name == "valueOf" }
                            val enumInstance = constructor.invoke(null, paramValue.value as String)
                            method.invoke(builder, enumInstance)
                        }
                        param.type.isPrimitiveOrString() || param.type.isPrimitive -> method.invoke(builder, paramValue)
                        else -> method.invoke(builder, (paramValue as ObjectArgument).toObject())
                    }
                }
            }
            builder.build()
        }
        NetworkOptions::class.java.name -> {
            val builder = NetworkOptions.builder()
            fieldMap.remove("pinningDisabledInDevelopment")?.let { pinningDisabledInDevelopment ->
                builder.setPinningDisabledInDevelopment((pinningDisabledInDevelopment as String).toBoolean())
            }
            val domainMappings = fieldMap.entries.filter { it.key != "domainMappings" }
                    .map { domainMapping ->
                        val domainValue = (domainMapping.value as ObjectArgument).value as HashMap<String, Any>
                        val methodName = when (domainMapping.key) {
                            "eventsDomain" -> "eventsMapping"
                            "configDomain" -> "configMapping"
                            "identityDomain" -> "identityMapping"
                            "audienceDomain" -> "audienceMapping"
                            else -> null
                        }
                        val method = DomainMapping::class.java.methods.firstOrNull { it.name == methodName }
                        val domainMappingObj = method?.invoke(null, domainValue["url"]) as DomainMapping.Builder
                        (domainValue["certificates"] as List<Map<String, String>>).forEach {
                            Certificate.with(it["alias"]!!, it["certificate"]!!)?.let { domainMappingObj.addCertificate(it) }
                        }
                        domainMappingObj.build()
                    }
            builder.setDomainMappings(domainMappings)
            builder.build()
        }
        IdentityApiRequest::class.java.name -> {
            val builder = if (fieldMap.contains("mpid")) {
                //TODO
                //how is this going to work?
                //we need a step before all this to set up the SDK state, probably by consuming the AllUsers (?)event
                IdentityApiRequest.withUser(fieldMap.remove("mpid"));
            } else {
                IdentityApiRequest.withEmptyUser()
            }
            val builder = IdentityApiRequest.withEmptyUser()
        }
        else -> null
    }
}

fun <T>Class<T>.invokeMethod(instance:T?, methodName: String, apiEvent: ApiCall) {
    methods.filter { it.name == methodName }
            .forEach { method ->
                if (method.parameters.size == apiEvent.objectArguments?.size) {
                    val arguments = apiEvent.objectArguments?.toObjects()
                    when (arguments?.size) {
                        0, null -> method.invoke(instance)
                        1 -> method.invoke(instance, arguments[0])
                        else -> method.invoke(instance, arguments.toList())
                    }
                }
            }
}
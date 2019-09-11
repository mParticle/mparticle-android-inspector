package com.mparticle.shared.events

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class ApiCall(var endpoint: String,
                   var arguments: List<ObjectArgument>,
                   var timeSent: Long,
                   //nullable because we do not always send the invoked object, only useful when it is stateful, like Cart w/an mpid, or MParticleUser
                   val objectArgument: ObjectArgument? = null,
                   var expanded: Boolean = false,
                   override var id: Int,
                   open var status: Status? = null): ChainableEvent() {
    override val name: String = endpoint

    open fun copy(): ApiCall {
        return ApiCall(this.name, arguments, timeSent, objectArgument, expanded, id, status)
    }

    fun getMethodName(): String {
        return endpoint.split(".")[0].replace("()", "")
    }

    fun getClassName(): String {
        return endpoint.split(".")[0]
    }
}

@Serializable
class KitApiCall(val kitId: Int,
                 var endpointName: String,
                 var kitObjectArguments: List<ObjectArgument>,
                 var kitTimeSent: Long,
                 var kitExpanded: Boolean = false,
                 @SerialName("kit_id")
                 override var id: Int,
                 @SerialName("kit_status")
                 override var status: Status?)
    : ApiCall(
        endpoint = endpointName,
        arguments = kitObjectArguments,
        timeSent = kitTimeSent,
        objectArgument = null,
        expanded = kitExpanded,
        id = id,
        status = status) {

    override fun copy(): KitApiCall {
        return KitApiCall(kitId, name, arguments, timeSent, expanded, id, status ?: Status.Red)
    }
}

@Serializable
data class ObjectArgument(val fullClassName: String,
                          @Polymorphic
                          val value: ObjectValue,
                          val id: Int? = null) {

    val className = fullClassName.split(".").last()
}

sealed class ObjectValue

class NullObject: ObjectValue()

@Serializable
class Primitive(@Polymorphic val value: Any): ObjectValue()

@Serializable
class EnumObject(val name: String): ObjectValue()

@Serializable
class Obj(val fields: MutableList<FieldObject>): ObjectValue()

@Serializable
class CollectionObject(val values: List<ObjectArgument>): ObjectValue()

@Serializable
class MapObject(val valuesMap: Map<ObjectArgument, ObjectArgument>): ObjectValue()


@Serializable
class FieldObject(val access: Int, val fieldName: String, val objectArgument: ObjectArgument, val isMethod: Boolean) {
    companion object {
        const val PRIVATE = 0
        const val PACKAGE_PRIVATE = 1
        const val PROTECTED = 2
        const val PUBLIC = 3
    }
}
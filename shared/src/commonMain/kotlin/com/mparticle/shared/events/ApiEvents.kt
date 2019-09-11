package com.mparticle.shared.events

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class ApiCall(var endpoint: String, var objectArguments: List<ObjectArgument>?, var timeSent: Long, var expanded: Boolean = false, override var id: Int, open var status: Status? = null): ChainableEvent() {
    override val name: String = endpoint

    open fun copy(): ApiCall {
        return ApiCall(this.name, objectArguments, timeSent, expanded, id, status)
    }
}

@Serializable
class KitApiCall(val kitId: Int, var endpointName: String, var kitObjectArguments: List<ObjectArgument>?, var kitTimeSent: Long, var kitExpanded: Boolean = false, @SerialName("kit_id")override var id: Int, @SerialName("kit_status") override var status: Status?): ApiCall(endpointName, kitObjectArguments, kitTimeSent, kitExpanded, id, status) {
    override fun copy(): KitApiCall {
        return KitApiCall(kitId, name, objectArguments, timeSent, expanded, id, status
                ?: Status.Red)
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

@Serializable
class Primitive(@Polymorphic val value: Any): ObjectValue()

@Serializable
class EnumObject(val name: String): ObjectValue()

@Serializable
class Obj(val fields: MutableList<FieldObject>): ObjectValue()

@Serializable
class CollectionObject(val values: List<ObjectArgument?>): ObjectValue()

@Serializable
class MapObject(val valuesMap: Map<ObjectArgument?, ObjectArgument?>): ObjectValue()


@Serializable
class FieldObject(val access: Int, val fieldName: String, val objectArgument: ObjectArgument?, val isMethod: Boolean) {
    companion object {
        const val PRIVATE = 0
        const val PACKAGE_PRIVATE = 1
        const val PROTECTED = 2
        const val PUBLIC = 3
    }
}
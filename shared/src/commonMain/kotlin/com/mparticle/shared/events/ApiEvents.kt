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
data class ObjectArgument(val className: String, val fullClassName: String, @Polymorphic val value: Any, val id: Int? = null, val isEnum: Boolean = false)
//
//class Value(val fieldName: String, val accessLevel: Int, val isPrimitive: Boolean, val primitiveValue: Any, val ObjectValue: List<ObjectArgument>) {
//
//    companion object {
//        const val PRIVATE = 0
//        const val PACKAGE_PRIVATE = 1
//        const val PROTECTED = 2
//        const val PUBLIC = 3
//    }
//}
package com.mparticle.inspector.events

import com.mparticle.inspector.customviews.Status

open class ApiCall(endpoint: String, var methodArguments: List<MethodArgument>?, var timeSent: Long, var expanded: Boolean = false, id: Int, var status: Status? = null): ChainableEvent(id, endpoint) {
    open fun copy(): ApiCall {
        return ApiCall(this.name, methodArguments, timeSent, expanded, id, status)
    }
}

class KitApiCall(val kitId: Int, endpoint: String, methodArguments: List<MethodArgument>?, timeSent: Long, expanded: Boolean = false, id: Int, status: Status): ApiCall(endpoint, methodArguments, timeSent, expanded, id, status) {
    override fun copy(): KitApiCall {
        return KitApiCall(kitId, name, methodArguments, timeSent, expanded, id, status
                ?: Status.Red)
    }
}

data class MethodArgument(val clazz: Class<Any>, val value: Any, val id: Int? = null)
package com.mparticle.shared.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NetworkRequest(var title: String,
                     var status: Status,
                     val url: String,
                     val body: String,
                     var timeSent: Long,
                     var bodyExpanded: Boolean = false,
                     var responseExpanded: Boolean = false,
                     var expanded: Boolean = false,
                     var responseCode: String = "-",
                     var responseBody: String? = null,
                     @SerialName("network_id") override var id: Int): ChainableEvent() {
    override val name: String = title
    fun copy(): NetworkRequest {
        return NetworkRequest(name, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id)
    }
}
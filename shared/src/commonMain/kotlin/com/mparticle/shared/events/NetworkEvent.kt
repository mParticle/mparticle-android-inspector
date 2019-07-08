package com.mparticle.shared.events

import kotlinx.serialization.json.JsonObject

class NetworkRequest(title: String,
                     var status: Status,
                     val url: String,
                     val body: String,
                     var timeSent: Long,
                     var bodyExpanded: Boolean = false,
                     var responseExpanded: Boolean = false,
                     var expanded: Boolean = false,
                     var responseCode: String = "-",
                     var responseBody: String? = null,
                     id: Int): ChainableEvent(id, title) {
    fun copy(): NetworkRequest {
        return NetworkRequest(name, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id)
    }
}
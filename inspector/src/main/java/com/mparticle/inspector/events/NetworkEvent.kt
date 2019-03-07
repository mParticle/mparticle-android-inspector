package com.mparticle.inspector.events

import com.mparticle.inspector.customviews.Status
import org.json.JSONObject

class NetworkRequest(title: String,
                     var status: Status,
                     val url: String,
                     val body: JSONObject,
                     var timeSent: Long,
                     var bodyExpanded: Boolean = false,
                     var responseExpanded: Boolean = false,
                     var expanded: Boolean = false,
                     var responseCode: String = "-",
                     var responseBody: JSONObject? = null,
                     id: Int): ChainableEvent(id, title) {
    fun copy(): NetworkRequest {
        return NetworkRequest(name, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id)
    }
}
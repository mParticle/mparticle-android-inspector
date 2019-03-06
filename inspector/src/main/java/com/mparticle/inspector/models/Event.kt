package com.mparticle.inspector.models

import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.customviews.Status
import org.json.JSONObject


data class ChainTitle(val title: String): Event(title)

open class Event(val name: String, val createdTime: Long = System.currentTimeMillis())

class Title(val title: String, val itemType: Int, var expanded: Boolean = true, var order: Order = Order.Chronological_Recent_First): Event(title)

open class Identified(val id: Int, name: String): Event(name)

open class StateGeneric(title: String, val priority: Int = 0, var expanded: Boolean = false): Event(title)


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
                     id: Int): Identified(id, title) {
    fun copy(): NetworkRequest {
        return NetworkRequest(name, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id)
    }
}

open class ApiCall(endpoint: String, var arguments: List<Argument>?, var timeSent: Long, var expanded: Boolean = false, id: Int, var status: Status? = null): Identified(id, endpoint) {
    open fun copy(): ApiCall {
        return ApiCall(this.name, arguments, timeSent, expanded, id, status)
    }
}

class KitApiCall(val kitId: Int, endpoint: String, arguments: List<Argument>?, timeSent: Long, expanded: Boolean = false, id: Int, status: Status): ApiCall(endpoint, arguments, timeSent, expanded, id, status) {
    override fun copy(): KitApiCall {
        return KitApiCall(kitId, name, arguments, timeSent, expanded, id, status ?: Status.Red)
    }
}

data class Kit(val kitId: Int, val kitName: String, var status: Status, var expanded: Boolean = false, var apiCalls: MutableList<ApiCall> = ArrayList(), var errorMessage: String? = null): Event(kitName)

class MessageQueued(tableName: String,
                    var body: JSONObject,
                    var status: Status,
                    var storedTime: Long = System.currentTimeMillis(),
                    var deleteTime: Long? = null,
                    var bodyExpanded: Boolean = false,
                    var rowId: Long,
                    id: Int): Identified(id, tableName)

class MessageTable(tableName: String, val messages: MutableMap<MessageQueued, Mutable<Boolean>> = HashMap(), var bodyExpanded: Boolean = false): Event(tableName)

data class StateCurrentUser(var user: MParticleUser?, var attributesExpanded: Boolean = false, var identitiesExpanded: Boolean = false): StateGeneric("Current User", 1)

data class StateAllUsers(var users: Map<MParticleUser, Mutable<Boolean>>): StateGeneric("Previous Users", 2)

class StateStatus(title: String, priority: Int, var status: () -> Status, var fields: MutableMap<String, Any> = HashMap(), var obj: Any? = null): StateGeneric(title, priority) {
    override fun equals(other: Any?): Boolean {
        return other is StateStatus && name == other.name
    }
}

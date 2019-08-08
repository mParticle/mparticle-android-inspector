package com.mparticle.shared.events


import com.mparticle.shared.PlatformApis
import com.mparticle.shared.utils.Mutable
import kotlinx.serialization.Serializable

@Serializable
open class Event(val name: String, val createdTime: Long = PlatformApis().getTimestamp()) {
    fun serialize(): String {
        return serializer().toString()
    }
}

open class ChainableEvent(val id: Int, name: String): Event(name)

data class Kit(val kitId: Int, val kitName: String, var status: Status, var expanded: Boolean = false, var apiCalls: MutableList<ApiCall> = ArrayList(), var errorMessage: String? = null): Event(kitName)

class MessageTable(tableName: String, val messages: MutableMap<MessageEvent, Mutable<Boolean>> = HashMap(), var bodyExpanded: Boolean = false): Event(tableName)


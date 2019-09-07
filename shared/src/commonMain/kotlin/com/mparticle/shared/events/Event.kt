package com.mparticle.shared.events


import com.mparticle.shared.PlatformApis
import com.mparticle.shared.utils.Mutable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
class EventCollection(val list: List<@Polymorphic Event>)

//@Serializable
abstract class Event() {
    open val createdTime: Long = PlatformApis().getTimestamp()
    abstract val name: String
}

//@Serializable
abstract class ChainableEvent(): Event() {
    abstract val id: Int
}

@Serializable
data class Kit(val kitId: Int, override val name: String, var status: Status, var expanded: Boolean = false, var apiCalls: MutableList<ApiCall> = ArrayList(), var errorMessage: String? = null): Event()

@Serializable
class MessageTable(override val name: String, val messages: MutableMap<MessageEvent, Mutable<Boolean>> = HashMap(), var bodyExpanded: Boolean = false): Event()


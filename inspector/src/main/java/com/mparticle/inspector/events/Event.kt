package com.mparticle.inspector.events

import com.mparticle.inspector.customviews.Status
import com.mparticle.inspector.utils.Mutable


open class Event(val name: String, val createdTime: Long = System.currentTimeMillis())

open class ChainableEvent(val id: Int, name: String): Event(name)

data class Kit(val kitId: Int, val kitName: String, var status: Status, var expanded: Boolean = false, var apiCalls: MutableList<ApiCall> = ArrayList(), var errorMessage: String? = null): Event(kitName)

class MessageTable(tableName: String, val messages: MutableMap<MessageEvent, Mutable<Boolean>> = HashMap(), var bodyExpanded: Boolean = false): Event(tableName)


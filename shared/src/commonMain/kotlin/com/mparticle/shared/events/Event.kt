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

    fun getShortName(): String {
        return when (this) {
            is NetworkRequest -> NETWORK
            is Kit -> KIT
            is KitApiCall -> KIT_API_CALL
            is MessageEvent -> DB
            is MessageTable -> DB
            is ApiCall -> API_CALL
            else -> NO_TITLE
        }
    }

    companion object ShortTitle {
        const val NETWORK = "Network"
        const val KIT = "Kit"
        const val KIT_API_CALL = "Kit API"
        const val DB = "DB"
        const val API_CALL = "API"
        const val NO_TITLE = "no title :("
    }

}

//@Serializable
abstract class ChainableEvent(): Event() {
    abstract val id: Int
}

@Serializable
data class Kit(val kitId: Int, override val name: String, var status: Status, var expanded: Boolean = false, var apiCalls: MutableList<ApiCall> = ArrayList(), var errorMessage: String? = null): Event()

@Serializable
class MessageTable(override val name: String, val messages: MutableMap<MessageEvent, Mutable<Boolean>> = HashMap(), var bodyExpanded: Boolean = false): Event()


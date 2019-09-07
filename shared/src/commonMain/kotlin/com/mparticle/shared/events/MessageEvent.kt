package com.mparticle.shared.events

import com.mparticle.shared.PlatformApis
import com.mparticle.shared.events.Status
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageEvent(private var tableName: String,
                   var body: String,
                   var status: Status,
                   var storedTime: Long = PlatformApis().getTimestamp(),
                   var deleteTime: Long? = null,
                   var bodyExpanded: Boolean = false,
                   var rowId: Long,
                   @SerialName("message_id")override var id: Int): ChainableEvent() {
    override val name: String = tableName
}
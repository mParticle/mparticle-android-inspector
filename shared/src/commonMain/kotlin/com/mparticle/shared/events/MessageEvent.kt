package com.mparticle.shared.events

import com.mparticle.shared.PlatformApis
import com.mparticle.shared.events.Status

class MessageEvent(tableName: String,
                   var body: String,
                   var status: Status,
                   var storedTime: Long = PlatformApis().getTimestamp(),
                   var deleteTime: Long? = null,
                   var bodyExpanded: Boolean = false,
                   var rowId: Long,
                   id: Int): ChainableEvent(id, tableName)
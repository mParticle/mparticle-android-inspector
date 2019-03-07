package com.mparticle.inspector.events

import com.mparticle.inspector.customviews.Status
import org.json.JSONObject

class MessageEvent(tableName: String,
                   var body: JSONObject,
                   var status: Status,
                   var storedTime: Long = System.currentTimeMillis(),
                   var deleteTime: Long? = null,
                   var bodyExpanded: Boolean = false,
                   var rowId: Long,
                   id: Int): ChainableEvent(id, tableName)
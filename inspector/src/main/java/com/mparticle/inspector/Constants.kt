package com.mparticle.inspector

import com.mparticle.inspector.events.*

class Constants {
    companion object {
        val SESSION_ID = "uuid: "
    }
}

enum class EventViewType {
    valTitle,
    valMessage,
    valMessageTable,
    valNetworkRequest,
    valApiCall,
    valKit,
    valStateGeneric,
    valStateCurrentUser,
    valStateList,
    valStateStatus,
    valChainTitle,
    valNext
}


fun Event.getDtoType() : EventViewType {
    return when (this) {
        is CategoryTitle -> EventViewType.valTitle
        is NetworkRequest -> EventViewType.valNetworkRequest
        is ApiCall -> EventViewType.valApiCall
        is Kit -> EventViewType.valKit
        is MessageEvent -> EventViewType.valMessage
        is StateEvent -> EventViewType.valStateGeneric
        is MessageTable -> EventViewType.valMessageTable
        is ChainTitle -> EventViewType.valChainTitle
        else -> throw RuntimeException("${javaClass.simpleName} not implemented for ViewType")
    }
}

fun Event.getShortName(): String {
    return when (this) {
        is NetworkRequest -> "Network"
        is Kit -> "Kit"
        is KitApiCall -> "Kit API"
        is MessageEvent -> "DB"
        is MessageTable -> "DB"
        is ApiCall -> "API"
        else -> "no title :("
    }
}
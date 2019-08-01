package com.mparticle.shared

import com.mparticle.shared.events.*

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
    valNext,
    valUnknown
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
        else -> EventViewType.valUnknown//throw RuntimeException("${this::class.simpleName} not implemented for ViewType")
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

fun <K, V> MutableMap<K, V>.putIfEmpty(key: K, value: V) {
    if (!containsKey(key)) {
        put(key, value)
    }
}

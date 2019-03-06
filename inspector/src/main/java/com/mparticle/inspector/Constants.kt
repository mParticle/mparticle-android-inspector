package com.mparticle.inspector

import com.mparticle.inspector.models.*


val valTitle = 0;
val valMessage = 1
val valMessageTable = 2
val valNetworkRequest = 3
val valApiCall = 4
val valKit = 5
val valStateGeneric = 6
val valStateCurrentUser = 7
val valStateAllUsers = 8
val valStateStatus = 9


fun Event.getDtoType() : Int {
    return when (this) {
        is Title -> valTitle
        is NetworkRequest -> valNetworkRequest
        is ApiCall -> valApiCall
        is Kit -> valKit
        is MessageQueued -> valMessage
        is StateGeneric -> valStateGeneric
        is MessageTable -> valMessageTable
        is ChainTitle -> -1
        else -> throw RuntimeException("${javaClass.simpleName} not implemented for ViewType")
    }
}

fun Event.getShortName(): String {
    return when (this) {
        is NetworkRequest -> "network"
        is ApiCall -> "API"
        is Kit -> "kit"
        is KitApiCall -> "kit API"
        is MessageQueued -> "msg"
        is MessageTable -> "msg"
        else -> "no title :("
    }
}
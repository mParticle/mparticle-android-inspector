package com.mparticle.shared

internal expect class PlatformApis() {
    fun getTimestamp(): Long
    fun print(message: String)
}
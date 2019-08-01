package com.mparticle.shared

internal actual class PlatformApis {
    actual fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

    actual fun print(message: String) {
        println(message)
    }
}
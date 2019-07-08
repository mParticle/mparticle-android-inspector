package com.mparticle.shared

import kotlin.js.Date

internal actual class PlatformApis {
    actual fun getTimestamp(): Long {
        return Date().getTime().toLong()
    }

    actual fun print(message: String) {
        console.log(message)
    }
}
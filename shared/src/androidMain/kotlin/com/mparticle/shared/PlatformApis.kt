package com.mparticle.shared

import android.util.Log

internal actual class PlatformApis {

    actual fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

    actual fun print(message:String) {
        Log.d("MParticle", message)
    }
}
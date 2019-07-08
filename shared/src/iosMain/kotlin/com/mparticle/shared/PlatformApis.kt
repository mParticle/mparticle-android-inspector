package com.mparticle.shared

import kotlinx.cinterop.objcPtr
import platform.Foundation.NSDate
import platform.Foundation.NSLog
import platform.Foundation.NSStringFromClass
import platform.Foundation.timeIntervalSince1970

internal actual class PlatformApis {

    actual fun getTimestamp(): Long {
        return NSDate().timeIntervalSince1970.toLong()
    }

    actual fun print(message:String) {
        NSLog(message)
    }

}
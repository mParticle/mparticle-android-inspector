package com.mparticle.inspector.utils

import android.os.Build
import java.util.*

class Utils {
    companion object {
        fun isSimulator(): Boolean {
            val model = Build.MODEL?.toLowerCase(Locale.getDefault());
            return model?.contains("emulator") == true || model?.contains("sdk") == true
        }
    }
}
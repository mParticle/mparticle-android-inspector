package com.mparticle

internal class AccessUtils {
    companion object {
        fun isLocationTracking(): Boolean {
            return MParticle.getInstance()?.isLocationTrackingEnabled() ?: false
        }

        fun getAppState(): String? {
            return MParticle.getAppState()
        }
    }
}
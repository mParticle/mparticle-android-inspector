package com.mparticle

internal class Access {
    companion object {
        fun isLocationTracking(): Boolean {
            return MParticle.getInstance()?.isLocationTrackingEnabled() ?: false
        }

        fun getAppState(): String? {
            return MParticle.getAppState()
        }
    }
}
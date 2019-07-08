package com.mparticle.shared

interface User {
    val id: Long
    val userAttributes: Map<String, Any>
    val userIdentities: Map<String, String?>
    
    val cart: Map<String, Any>
    val consentState: Map<String, Any>

    val loggedIn: Boolean
    val firstSeenTime: Long
    val lastSeenTime: Long
}
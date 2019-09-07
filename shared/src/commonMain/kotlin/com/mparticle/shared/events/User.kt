package com.mparticle.shared

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
open class User (val id: Long,
                 open val userAttributes: Map<String, @Polymorphic Any>,
                 open val userIdentities: Map<String, String?>,
                 open val cart: Map<String, @Polymorphic Any?>,
                 open val consentState: Map<String, @Polymorphic Any?>,
                 open val loggedIn: Boolean,
                 open val firstSeenTime: Long,
                 open val lastSeenTime: Long)
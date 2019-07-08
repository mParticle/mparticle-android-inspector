package com.mparticle.inspector

import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.utils.printClass
import com.mparticle.inspector.utils.toMap
import com.mparticle.shared.User
import org.json.JSONObject

class UserWrapper(val user: MParticleUser): User {

    override val id: Long = user.id

    override val userAttributes: Map<String, Any> = user.userAttributes

    override val userIdentities: Map<String, String?> = user.userIdentities.entries.associate { it.key.name to it.value }

    override val cart: Map<String, Any> = (user.cart.printClass() as JSONObject).toMap()

    override val consentState: Map<String, Any> = (user.consentState.printClass() as JSONObject).toMap()

    override val loggedIn: Boolean = user.isLoggedIn

    override val firstSeenTime: Long = user.firstSeenTime

    override val lastSeenTime: Long = user.lastSeenTime

}
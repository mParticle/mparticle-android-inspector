package com.mparticle.inspector.events

import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.customviews.Status
import com.mparticle.inspector.utils.Mutable
import com.mparticle.internal.InternalSession

open class StateEvent(title: String, var priority: Int = 0, var expanded: Boolean = false): Event(title)

data class StateCurrentUser(var user: MParticleUser?, var attributesExpanded: Boolean = false, var identitiesExpanded: Boolean = false): StateEvent("Current User")

data class StateAllUsers(var users: Map<MParticleUser, Mutable<Boolean>>): StateEvent("Previous Users")

data class StateAllSessions(var sessions: MutableMap<StateStatus, Mutable<Boolean>>): StateEvent("Previous Sessions")

class StateStatus(title: String, priority: Int, var status: () -> Status, var fields: MutableMap<String, Any> = HashMap(), var obj: Any? = null): StateEvent(title, priority) {
    override fun equals(other: Any?): Boolean {
        return other is StateStatus && name == other.name
    }
}
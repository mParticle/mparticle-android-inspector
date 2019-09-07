package com.mparticle.shared.events

import com.mparticle.shared.User
import com.mparticle.shared.events.Status
import com.mparticle.shared.utils.Mutable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class StateEvent(override var name: String, var priority: Int = 0, var expanded: Boolean = false): Event()

@Serializable
data class StateCurrentUser(var user: User?, var attributesExpanded: Boolean = false, var identitiesExpanded: Boolean = false): StateEvent("Current User")

@Serializable
data class StateAllUsers(var users: Map<User, Mutable<Boolean>>): StateEvent("Previous Users")

@Serializable
data class StateAllSessions(var sessions: MutableMap<StateStatus, Mutable<Boolean>>): StateEvent("Previous Sessions")

@Serializable
class StateStatus(private var title: String, private var priorityy: Int, @Transient var status: (() -> Status)? = null, var fields: Map<String, @Polymorphic Any> = HashMap(), @Transient var obj: Any? = null): StateEvent(title, priorityy) {
    override fun equals(other: Any?): Boolean {
        return other is StateStatus && name == other.name
    }
}
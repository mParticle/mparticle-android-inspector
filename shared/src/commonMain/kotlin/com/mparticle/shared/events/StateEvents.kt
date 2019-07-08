package com.mparticle.shared.events

import com.mparticle.shared.User
import com.mparticle.shared.events.Status
import com.mparticle.shared.utils.Mutable

open class StateEvent(title: String, var priority: Int = 0, var expanded: Boolean = false): Event(title)

data class StateCurrentUser(var user: User?, var attributesExpanded: Boolean = false, var identitiesExpanded: Boolean = false): StateEvent("Current User")

data class StateAllUsers(var users: Map<User, Mutable<Boolean>>): StateEvent("Previous Users")

data class StateAllSessions(var sessions: MutableMap<StateStatus, Mutable<Boolean>>): StateEvent("Previous Sessions")

class StateStatus(title: String, priority: Int, var status: () -> Status, var fields: MutableMap<String, Any> = HashMap(), var obj: Any? = null): StateEvent(title, priority) {
    override fun equals(other: Any?): Boolean {
        return other is StateStatus && name == other.name
    }
}
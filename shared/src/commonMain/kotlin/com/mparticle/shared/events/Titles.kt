package com.mparticle.shared.events

import com.mparticle.shared.EventViewType

data class ChainTitle(override val name: String): Event()

class CategoryTitle(override val name: String, val itemType: EventViewType, var expanded: Boolean = true, var order: Order = Order.Chronological_Recent_First): Event() {
    var count: Int = 0
    fun toggleExpanded() {
        onExpand?.invoke(!expanded)
    }

    internal var onExpand: ((Boolean) -> Unit)? = null
}

enum class Order {
    Alphbetical,
    Chronological_Recent_First,
    Custom
}
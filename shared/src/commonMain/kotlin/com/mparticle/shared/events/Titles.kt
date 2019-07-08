package com.mparticle.shared.events

import com.mparticle.shared.EventViewType

data class ChainTitle(val title: String): Event(title)

class CategoryTitle(val title: String, val itemType: EventViewType, var expanded: Boolean = true, var order: Order = Order.Chronological_Recent_First): Event(title) {
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
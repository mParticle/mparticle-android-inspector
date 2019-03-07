package com.mparticle.inspector.events

import com.mparticle.inspector.EventViewType

data class ChainTitle(val title: String): Event(title)

class CategoryTitle(val title: String, val itemType: EventViewType, var expanded: Boolean = true, var order: Order = Order.Chronological_Recent_First): Event(title)

enum class Order {
    Alphbetical,
    Chronological_Recent_First,
    Custom
}
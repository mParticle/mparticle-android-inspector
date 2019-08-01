package com.mparticle.shared

import com.mparticle.shared.events.*
import com.mparticle.shared.controllers.CategoryController
import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.utils.Mutable

class ViewControllerManager {
    val streamController = StreamController()
    val categoryController = CategoryController { activeKits[it] }
    val allEvents: List<Event>
        get() = ArrayList(events)

    private val activeKits = HashMap<Int, Kit>()
    private val events = ArrayList<Event>()


    fun addEvent(item: Event) {
        if (events.contains(item)) {
            streamController.refreshItem(item)
            categoryController.refreshItem(item)
        } else {
            if (item is Kit) {
                activeKits.put(item.kitId, item)
            }
            events.add(item)
            streamController.addItem(item)
            categoryController.addItem(item)
        }
    }
}
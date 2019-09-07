package com.mparticle.shared.controllers

import com.mparticle.shared.events.*
import com.mparticle.shared.EventViewType
import com.mparticle.shared.getDtoType
import com.mparticle.shared.utils.Mutable

class StreamController: BaseController() {
    var eventCountLimit = 100

    override fun addItem(item: Event) {
        var obj = item
        if ((obj is CategoryTitle || obj.getDtoType() == EventViewType.valStateGeneric) && !(obj is KitApiCall)) {
            return
        }
        if (obj is MessageEvent) {
            obj = MessageTable(obj.name).apply {
                messages.put(obj as MessageEvent, Mutable(false))
            }
        }
        var objects = getEvents()
        if (objects.size > eventCountLimit) {
            onRemoved(objects.subList(0, eventCountLimit - 1), eventCountLimit - 1, objects.size - eventCountLimit)
        }

        objects = getEvents()
        objects
            .indexOfFirst { obj.createdTime > it.createdTime }
            .let {
                if (it > 0) {
                    objects.add(it - 1, obj)
                    onAdded(objects, it - 1, obj)
                } else {
                    objects.add(0, obj)
                    onAdded(objects, 0, obj)
                }
            }
    }

    override fun refreshItem(item: Event) {
        onRefreshed(item)
    }
}
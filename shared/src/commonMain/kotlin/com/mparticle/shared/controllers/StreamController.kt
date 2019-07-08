package com.mparticle.shared.controllers

import com.mparticle.shared.events.*
import com.mparticle.shared.EventViewType
import com.mparticle.shared.getDtoType
import com.mparticle.shared.utils.Mutable

class StreamController: BaseController() {
    val itemLimit = 100

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
        var objectList = getObjects()
        objectList
                .indexOfFirst { obj.createdTime > it.createdTime }
                .let {
                    if (objectList.size > itemLimit) {
                        onRemoved(objectList.subList(0, itemLimit - 1), itemLimit - 1, objectList.size - itemLimit)
                    }
                    if (it > 0) {
                        objectList.add(it, obj)
                        onAdded(objectList, it, obj)
                    } else {
                        objectList.add(0, obj)
                        onAdded(objectList, 0, obj)
                    }
                }
    }

    override fun refreshItem(item: Event) {
        onRefreshed(item)
    }
}
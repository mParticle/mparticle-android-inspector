package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.mparticle.inspector.*
import com.mparticle.inspector.events.*
import com.mparticle.inspector.viewholders.*
import com.mparticle.inspector.utils.Mutable
import com.mparticle.inspector.utils.visible
import java.util.*

class RecentListAdapter(context: Context, dataManager: DataManager, objectMap: MutableMap<EventViewType, LinkedHashSet<Event>> = HashMap(), displayCallback: (Int) -> Unit, startTime: Long): BaseListAdapter(context, startTime, displayCallback, dataManager) {

    val itemLimit = 100

    companion object {
        val lock = Object()
    }

    init {
        objectMap.values
                .fold(ArrayList<Event>()) { acc, list ->
                    acc.apply { addAll(list) }
                }
                .forEach { addItem(it) }
    }

    override fun createAllViewHolders(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.titleView?.visible(true)
        }
    }

    override fun bindAllViewHolders(holder: RecyclerView.ViewHolder?, event: Event) {
        if (holder is ItemViewHolder) {
            holder.titleView?.text = event.getShortName()
        }
    }

    override fun addItem(event: Event) {
        var obj = event
        synchronized(lock) {
            if ((obj is CategoryTitle || obj.getDtoType() == EventViewType.valStateGeneric) && !(event is KitApiCall)) {
                return
            }
            if (obj is MessageEvent) {
                obj = MessageTable(obj.name).apply {
                    messages.put(obj as MessageEvent, Mutable(false))
                }
            }
            var objects = getObjects()
            objects
                    .indexOfFirst { obj.createdTime > it.createdTime }
                    .let {
                        if (objects.size > itemLimit) {
                            objects = objects.subList(0, itemLimit)
                        }
                        if (it > 0) {
                            objects.add(it, obj)
                            refreshData(objects, it, removed = false)
                        } else {
                            objects.add(0, obj)
                            refreshData(objects, 0, removed = false)
                        }
                    }
        }
    }
}
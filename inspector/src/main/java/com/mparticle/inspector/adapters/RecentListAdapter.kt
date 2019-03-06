package com.mparticle.inspector.adapters

import android.content.Context
import android.os.Looper
import android.support.v7.widget.RecyclerView
import com.mparticle.inspector.*
import com.mparticle.inspector.models.*
import com.mparticle.inspector.utils.visible
import java.util.*

class RecentListAdapter(context: Context, objectMap: MutableMap<Int, LinkedHashSet<Event>> = HashMap(), displayCallback: (Int) -> Unit, startTime: Long): BaseListAdapter(context, startTime, displayCallback) {

    val itemLimit = 100

    companion object {
        val lock = Object()
    }

    init {
        objectMap.values
                .fold(ArrayList<Event>()) { acc, list ->
                    acc.apply { addAll(list)}
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
        if (getObjects().size == 0) {
            displayCallback.invoke(-1)
        }
        synchronized(lock) {
            if (obj is Title || obj.getDtoType() == valStateGeneric) {
                return
            }
            if (obj is MessageQueued) {
                obj = MessageTable(obj.name).apply {
                    messages.put(obj as MessageQueued, Mutable(false))
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
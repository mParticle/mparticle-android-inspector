package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.mparticle.internal.Logger
import com.mparticle.inspector.*
import com.mparticle.inspector.EventViewType.*
import com.mparticle.inspector.events.*
import com.mparticle.inspector.viewholders.*
import com.mparticle.inspector.utils.Mutable
import com.mparticle.inspector.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet
import kotlin.collections.MutableMap
import kotlin.collections.first
import kotlin.collections.firstOrNull
import kotlin.collections.forEach
import kotlin.collections.indexOfFirst
import kotlin.collections.remove

open class CategorizedListAdapter(context: Context, var objectMap: MutableMap<EventViewType, LinkedHashSet<Event>> = HashMap(), displayCallback: (Int) -> Unit, startTime: Long): BaseListAdapter(context, startTime, displayCallback) {
    val messageTableMap = HashMap<String, MessageTable>()

    init {
        objectMap.apply {
            putIfEmpty(EventViewType.valTitle, LinkedHashSet())
            putIfEmpty(valStateGeneric, LinkedHashSet())
            putIfEmpty(valApiCall, LinkedHashSet())
            putIfEmpty(valNetworkRequest, LinkedHashSet())
            putIfEmpty(EventViewType.valMessage, LinkedHashSet())
            putIfEmpty(valMessageTable, LinkedHashSet())
            putIfEmpty(valKit, LinkedHashSet())
        }
        addItem(CategoryTitle(titleState, valStateGeneric, order = Order.Custom))
        addItem(CategoryTitle(titleApiCall, valApiCall))
        addItem(CategoryTitle(titleKit, valKit, order = Order.Alphbetical))
        addItem(CategoryTitle(titleNetworkRequest, valNetworkRequest))
        addItem(CategoryTitle(titleStoredMessages, valMessageTable, order = Order.Alphbetical))
        objectMap.values.forEach { it.forEach { addItem(it, false) } }
    }

    override fun createAllViewHolders(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.titleView?.visible(false)
        }
        if (!(viewHolder is TitleViewHolder)) {
            (viewHolder.itemView.layoutParams as RecyclerView.LayoutParams).apply {
                var density = context.resources.displayMetrics.density
                leftMargin = (density * 12 + .5).toInt()
            }
        }
    }

    override fun bindTitleVH(viewHolder: TitleViewHolder, obj: CategoryTitle) {
        super.bindTitleVH(viewHolder, obj)
        fun setCount() {
            val count = objectMap.get(obj.itemType)?.size ?: 0
            viewHolder.count.apply {
                visible(!obj.expanded || count == 0)
                text = count.toString()
            }
        }
        viewHolder.apply {
            setCount()
            itemView.setOnClickListener {
                obj.expanded = !obj.expanded
                setCount()
                if (obj.expanded) {
                    objectMap.get(obj.itemType)?.let {
                        GlobalScope.launch(Dispatchers.Main) {
                            ArrayList<Event>(it).forEach { addItem(it, new = false) }
                        }
                    }
                } else {
                    var found = false
                    val objects = getObjects()
                    objects.subList(objects.indexOf(obj) + 1, objects.size).let {
                        var count = 0
                        ArrayList<Any>(it).forEach {
                            if (it is CategoryTitle) {
                                found = true
                            } else {
                                if (!found) {
                                    count++
                                    objects.remove(it)
                                }
                            }
                        }
                        if (count > 0) {
                            refreshData(objects, position = getObjects().indexOf(obj) + 1, count = count, removed = true)
                        }
                    }
                }
            }
        }
    }

    override fun addItem(event: Event) {
        addItem(event, true)
    }

    open fun addItem(obj: Event, new: Boolean? = true) {
        when (obj) {
            is NetworkRequest -> {
                addItemToList(titleNetworkRequest, obj, new)
            }
            is ApiCall -> addItemToList(titleApiCall, obj, new)
            is Kit -> addItemToList(titleKit, obj, new)
            is MessageEvent -> {
                var newMessageTable = false
                if (!messageTableMap.containsKey(obj.name)) {
                    newMessageTable = true
                    messageTableMap.put(obj.name, MessageTable(obj.name))
                }
                messageTableMap.get(obj.name)?.let {
                    it.messages.put(obj, Mutable(obj.bodyExpanded))
                    addItemToList(titleStoredMessages, it, newMessageTable)
                    refreshDataObject(it)
                }
            }
            is CategoryTitle -> {
                if (objectMap.get(valTitle)?.firstOrNull() { it is CategoryTitle && it.title == obj.title } == null) {
                    objectMap.get(valTitle)?.add(obj)
                }
                val objects = getObjects()
                if (objects.firstOrNull { it is CategoryTitle && it.title == obj.title } == null) {
                    objects.add(obj)
                }
                refreshData(objects)
            }
            is StateEvent -> addItemToList(titleState, obj, new)
            is MessageTable -> addItemToList(titleStoredMessages, obj, false)

            else -> throw RuntimeException("Unimplemented Item title: ${obj.javaClass.name}")
        }
    }

    private fun addItemToList(title: String, obj: Event, new: Boolean? = null) {
        when (new) {
            true -> objectMap[obj.getDtoType()]!!.add(obj) //if this crashes, you forgot to add a title ;)
        }
        val titleDto = objectMap.get(valTitle)?.first {
            it is CategoryTitle && it.title == title
        }
        if ((titleDto as CategoryTitle).expanded) {
            val objects = getObjects()
            var indexToAdd = objects.indexOf(titleDto) + 1
            val maxIndex = objects.subList(indexToAdd, objects.size)
                    .indexOfFirst { it is CategoryTitle }
                    .let {
                        if (it >= 0) {
                            it + indexToAdd
                        } else {
                            objects.size
                        }
                    }
            if (indexToAdd < objects.size) {
                when (titleDto.order) {
                    Order.Chronological_Recent_First -> {
                    }//do nothing, default
                    Order.Custom -> {
                        if (obj is StateEvent) {
                            val prioritizedIndex = objects
                                    .subList(indexToAdd, maxIndex + 1)
                                    .indexOfFirst {
                                        if (it is StateEvent) {
                                            obj.priority > it.priority
                                        } else {
                                            false
                                        }
                                    }
                            if (prioritizedIndex > 0) {
                                indexToAdd = prioritizedIndex
                            } else {
                                indexToAdd = maxIndex
                            }
                        }
                    }
                    Order.Alphbetical -> {
                        val alphabeticIndex = objects.subList(indexToAdd, maxIndex).indexOfFirst {
                            obj.name.compareTo(it.name) < 0
                        }
                        if (alphabeticIndex >= 0) {
                            indexToAdd += alphabeticIndex
                        } else {
                            indexToAdd = maxIndex
                        }
                    }
                }
            }
            if (!objects.contains(obj)) {
                objects.add(indexToAdd, obj)
                Logger.error("Add obj: ${obj.name}, pos: $indexToAdd")
                refreshData(objects, position = indexToAdd, removed = false)
            } else {
                refreshData(objects, indexToAdd, null)
            }
        }
    }
}

fun ArrayList<Any>.replace(replace: Any, replacement: Any): Boolean {
    val index = indexOf(replace)
    if (index >= 0) {
        removeAt(index)
        add(index, replacement)
        return true
    }
    return false
}

fun <K, V> MutableMap<K, V>.putIfEmpty(key: K, value: V) {
    if (!containsKey(key)) {
        put(key, value)
    }
}

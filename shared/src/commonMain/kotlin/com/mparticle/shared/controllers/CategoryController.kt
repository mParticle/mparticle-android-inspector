package com.mparticle.shared.controllers

import com.mparticle.shared.events.*
import com.mparticle.shared.EventViewType
import com.mparticle.shared.getDtoType
import com.mparticle.shared.putIfEmpty
import com.mparticle.shared.utils.Mutable

class CategoryController(private val activeKitsCallback: (Int) -> Kit?): BaseController() {

    private val titleStoredMessages = "Database State"
    private val titleNetworkRequest = "Network Usage"
    private val titleApiCall = "Api Usage"
    private val titleKit = "Kit State"
    private val titleState = "SDK State"

    private var objectMap: MutableMap<EventViewType, LinkedHashSet<Event>> = HashMap()
    private val messageTableMap = HashMap<String, MessageTable>()

    internal val categoryTitles = listOf(
            CategoryTitle(titleState, EventViewType.valStateGeneric, order = Order.Custom),
            CategoryTitle(titleApiCall, EventViewType.valApiCall),
            CategoryTitle(titleKit, EventViewType.valKit, order = Order.Alphbetical),
            CategoryTitle(titleNetworkRequest, EventViewType.valNetworkRequest),
            CategoryTitle(titleStoredMessages, EventViewType.valMessageTable, order = Order.Alphbetical))


    init {
        objectMap.apply {
            putIfEmpty(EventViewType.valTitle, LinkedHashSet())
            putIfEmpty(EventViewType.valStateGeneric, LinkedHashSet())
            putIfEmpty(EventViewType.valApiCall, LinkedHashSet())
            putIfEmpty(EventViewType.valNetworkRequest, LinkedHashSet())
            putIfEmpty(EventViewType.valMessage, LinkedHashSet())
            putIfEmpty(EventViewType.valMessageTable, LinkedHashSet())
            putIfEmpty(EventViewType.valKit, LinkedHashSet())
        }
        categoryTitles.forEach { addItem(it) }
    }

    override fun addItem(item: Event) {
        addItem(item, true)
    }

    override fun refreshItem(item: Event) {
        onRefreshed(item)
    }

    fun addItem(obj: Event, new: Boolean? = true) {
        when (obj) {
            is NetworkRequest -> {
                addItemToList(titleNetworkRequest, obj, new)
            }
            is KitApiCall -> {
                activeKitsCallback(obj.kitId)?.let {
                    it.apiCalls.add(obj)
                    onRefreshed(it)
                }
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
                    onRefreshed(it)
                }
            }
            is CategoryTitle -> {
                obj.onExpand = { onExpandCallback(it, obj) }
                if (objectMap.get(EventViewType.valTitle)?.firstOrNull() { it is CategoryTitle && it.name == obj.name } == null) {
                    objectMap.get(EventViewType.valTitle)?.add(obj)
                }
                val objects = getEvents()
                if (objects.firstOrNull { it is CategoryTitle && it.name == obj.name } == null) {
                    objects.add(obj)
                }
                onListUpdate(objects)
            }
            is StateEvent -> addItemToList(titleState, obj, new)
            is MessageTable -> addItemToList(titleStoredMessages, obj, false)

            else -> throw RuntimeException("Unimplemented Item title: ${obj::class.simpleName}")
        }
    }

    private fun addItemToList(title: String, obj: Event, new: Boolean? = null) {
        when (new) {
            true -> objectMap[obj.getDtoType()]!!.add(obj) //if this crashes, you forgot to add a title ;)
        }
        val titleDto = objectMap.get(EventViewType.valTitle)?.first {
            it is CategoryTitle && it.name == title
        }
        if ((titleDto as CategoryTitle).expanded) {
            val objects = getEvents()
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
                titleDto.count++
                objects.add(indexToAdd, obj)
                onAdded(objects, indexToAdd, obj)
            } else {
                onRefreshed(obj, indexToAdd)
            }
        }
        titleDto.count = objectMap[titleDto.itemType]?.size ?: 0
        onRefreshed(titleDto)
    }

    private var onExpandCallback: (expanded: Boolean, obj: CategoryTitle) -> Unit = { expanded, obj ->
        obj.expanded = expanded
        obj.count = objectMap.get(obj.itemType)?.size ?: 0
        if (obj.expanded) {
            objectMap.get(obj.itemType)?.let {
                ArrayList(it).forEach { addItem(it, new = false) }

            }
        } else {
            var found = false
            val objects = getEvents()
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
                    onRemoved(objects, getEvents().indexOf(obj) + 1, count)
                }
            }
        }
        onRefreshed(obj)
    }
}

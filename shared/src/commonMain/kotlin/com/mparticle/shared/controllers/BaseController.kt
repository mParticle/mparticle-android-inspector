package com.mparticle.shared.controllers

import com.mparticle.shared.events.Event

abstract class BaseController internal constructor() {

    //TODO after runnning Native, if we get "frozen" exceptions, we might need to change this
    //to a SharedLinkedList type, which has concurrency on Native
    var events: List<Event> = ArrayList()
        get() = ArrayList(field)
        private set
    private val refreshListeners: MutableList<((Int, Event) -> Unit)?> = ArrayList()
    private val addItemListeners: MutableList<((Int, Event) -> Unit)?> = ArrayList()
    private val removeListener: MutableList<((Int, Int) -> Unit)?> = ArrayList()
    private val listUpdatedListener: MutableList<((List<Event>) -> Unit)?> = ArrayList()

    fun registerAddedListener(listener: (position: Int, item: Event) -> Unit) {
        addItemListeners.add(listener)
    }

    fun registerRefreshListener(listener: (position: Int, item: Event) -> Unit) {
        refreshListeners.add(listener)
    }

    fun registerRemovedListener(listener: (position: Int, count: Int) -> Unit) {
        removeListener.add(listener)
    }

    fun registerListUpdatedListener(listener: (List<Event>) -> Unit) {
        listUpdatedListener.add(listener)
    }

    fun getItems(): List<Event> {
        return events
    }

    internal abstract fun addItem(item: Event)

    internal abstract fun refreshItem(item: Event)

    protected fun onAdded(objectList: List<Event>, position: Int, item: Event) {
        events = objectList
        addItemListeners.forEach { it?.invoke(position, item) }
    }

    protected fun onRefreshed(item: Event, position: Int? = getObjects().indexOf(item)) {
        if (position != null) {
            refreshListeners.forEach { it?.invoke(position, item) }
        }
    }

    protected fun onRemoved(objectList: List<Event>, position: Int, count: Int = 1) {
        events = objectList
        removeListener.forEach { it?.invoke(position, count) }
    }

    protected fun onListUpdate(list: List<Event>) {
        events = list
        listUpdatedListener.forEach { it?.invoke(ArrayList(list)) }
    }

    protected fun getObjects(): MutableList<Event> {
        return ArrayList(events)
    }
}
package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mparticle.InternalListenerImpl
import com.mparticle.ListenerImplementation
import com.mparticle.inspector.*
import com.mparticle.inspector.customviews.Status
import com.mparticle.inspector.models.*
import com.mparticle.inspector.utils.visible

class ChainListAdapter(context: Context, displayCallback: (Int) -> Unit, startTime: Long, private var itemId: Int, val listenerImplementation: ListenerImplementation) : BaseListAdapter(context, startTime, displayCallback) {

    init {
        initialize()
    }

    fun setItem(id: Int) {
        itemId = id
        initialize()
    }

    fun initialize() {
        listenerImplementation.addCompositesUpdatedListener(true) { childrensParents, parentsChildren ->
            val chains = ArrayList<LinkedHashSet<Identified>>()
            childrensParents[itemId]?.forEach { chains.add(it) }
            parentsChildren[itemId]?.forEach { chains.add(LinkedHashSet(it.reversed()))}

            val uniqueChains = chains.fold(ArrayList<LinkedHashSet<Identified>>()) { acc, set ->
                if (chains.all { !it.containsAll(set) || it == set } && !set.all { it is ApiCall }) {
                    acc.add(set)
                }
                acc
            }
            setChains(uniqueChains)
            false
        }
    }

    override fun addItem(event: Event) {
        //do nothing
    }

    private fun setChains(objs: List<Set<Identified>>) {
        val newObjects = ArrayList<Event>()

        //Nest objects properly into their top level, display object.
        // For example, a MessageDto, should be nest inside of a MessageTableDTO
        objs.forEach { list ->
            newObjects.add(ChainTitle(getChainTitle(list)))
            var messageTableDto: MessageTable? = null
            var kitDto: Kit? = null
            list.forEach { obj ->
                when (obj) {
                    is KitApiCall -> {
                        if (kitDto?.kitId != obj.kitId) {
                            kitDto = listenerImplementation.activeKits.get(obj.kitId)?.copy()
                            kitDto?.apiCalls?.clear()
                        }
                        if (kitDto == null) {
                            kitDto = Kit(obj.kitId, InternalListenerImpl.instance().getKitName(obj.kitId), Status.Red)
                        }
                        kitDto?.apply {  apiCalls.add(obj.copy().apply { expanded = false }) }
                    }
                    is ApiCall -> {
                        obj.copy().apply { expanded = false }
                    }
                    is MessageQueued -> {
                        if (messageTableDto?.name != obj.name) {
                            messageTableDto = MessageTable(obj.name)
                        }
                        messageTableDto?.apply {
                            messages.put(obj, Mutable(false))
                            if (messages.size > 1) {
                                bodyExpanded = true
                            }
                        }
                    }
                    is NetworkRequest -> obj.copy().apply {
                        this.expanded = false
                        this.bodyExpanded = false
                        this.responseExpanded = false
                    }
                    else -> obj
                }?.let {
                    if (newObjects.lastOrNull() != it) {
                        val previousObject = newObjects.lastOrNull()
                        if (!shouldSkipNextArrow(previousObject, it)) {
                            newObjects.add(Next())
                        }
                        newObjects.add(it)
                    }
                }
            }
        }
        refreshData(newObjects)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            101 -> ChainTitleViewHolder(inflater.inflate(R.layout.item_recyclerview_chain_title, parent, false))
            102 -> NextViewHolder(inflater.inflate(R.layout.item_recyclerview_and_then_arrow, parent, false))
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChainTitleViewHolder) {
            val obj = getObjects()[position] as ChainTitle
            holder.title.text = obj.title
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getViewType(obj: Any): Int {
        if (obj is ChainTitle) {
            return 101
        }
        if (obj is Next) {
            return 102
        }
        return super.getViewType(obj)
    }

    override fun bindTitleVH(viewHolder: TitleViewHolder, obj: Title) {
        super.bindTitleVH(viewHolder, obj)
        viewHolder.dropDown.visible(false)
    }

    override fun onHasConnections(id: Int?, onConnectable: (Boolean) -> Unit) {
        if (id != itemId) {
            super.onHasConnections(id, onConnectable)
        }
    }

    private fun getChainTitle(chain: Collection<Event>): String {
        return when (chain.size) {
            0 -> "Empty"
            1 -> chain.first().name + " to ..."
            else -> chain.first().name + " to " + chain.last().getEndChainName()
        }
    }

    private fun shouldSkipNextArrow(previousItem: Event?, nextItem: Event): Boolean {
        return (previousItem?.getDtoType() == valApiCall && nextItem.getDtoType() == valApiCall) ||
                previousItem == null ||
                previousItem is ChainTitle
    }

    fun Event.getEndChainName(): String {
        return when(this) {
            is NetworkRequest, is Kit -> getShortName().capitalize()
            is KitApiCall -> InternalListenerImpl.instance().getKitName(kitId) + " Kit"
            else -> " ..."
        }
    }

    class Next: Event("next")
    inner class NextViewHolder(imageView: View): RecyclerView.ViewHolder(imageView)
}

fun Event.copy(): Event {
    return when(this) {
        is ApiCall -> copy().apply { expanded = false }
        is NetworkRequest -> this.copy().apply { expanded = false }
        is KitApiCall -> this.copy().apply { expanded = false }
        is MessageTable -> MessageTable(name, messages, false)
        is MessageQueued -> MessageQueued(name, body, status, storedTime, bodyExpanded = false, rowId = rowId, id = id)
        is Kit -> this.copy().apply { apiCalls = ArrayList(apiCalls) }
        is StateAllUsers -> StateAllUsers(users)
        is StateCurrentUser -> StateCurrentUser(user)
        is StateStatus -> StateStatus(name, priority, status, fields)
        is StateGeneric -> StateGeneric(name, priority)
        is Title -> Title(title, itemType, false, order)
        is ChainListAdapter.Next -> ChainListAdapter.Next()
        is Identified -> Identified(id, name)
        is ChainTitle -> ChainTitle(name)
        else -> Event(name)
    }
}
package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mparticle.inspector.*
import com.mparticle.shared.events.Status
import com.mparticle.shared.events.*
import com.mparticle.inspector.views.*
import com.mparticle.shared.utils.Mutable
import com.mparticle.inspector.extensions.visible
import com.mparticle.shared.EventViewType
import com.mparticle.shared.getDtoType

class ChainListAdapter(context: Context, dataManager: DataManager, displayCallback: (Int) -> Unit, startTime: Long, private var itemId: Int) : BaseListAdapter(context, startTime, displayCallback, dataManager) {

    init {
        initialize()
    }

    fun setItem(id: Int) {
        itemId = id
        initialize()
    }

    fun initialize() {
        dataManager.addCompositesUpdatedListener(true) { childrensParents, parentsChildren ->
            val chains = ArrayList<LinkedHashSet<ChainableEvent>>()
            childrensParents[itemId]?.forEach { chains.add(it) }
            parentsChildren[itemId]?.forEach { chains.add(LinkedHashSet(it.reversed()))}

            val uniqueChains = chains.fold(ArrayList<LinkedHashSet<ChainableEvent>>()) { acc, set ->
                if (chains.all { !it.containsAll(set) || it == set } && !set.all { it is ApiCall && !(it is KitApiCall)  }) {
                    acc.add(set)
                }
                acc
            }
            setChains(uniqueChains.sortedBy { it.last().createdTime })
            false
        }
    }

    private fun setChains(objs: List<Set<ChainableEvent>>) {
        val newObjects = ArrayList<Event>()

        //Nest events properly into their top level, display object.
        // For example, a MessageDto, should be nest inside of a MessageTableDTO
        objs.forEach { list ->
            newObjects.add(ChainTitle(getChainTitle(list)))
            var messageTableDto: MessageTable? = null
            var kitDto: Kit? = null
            list.forEach { obj ->
                when (obj) {
                    is KitApiCall -> {
                        if (kitDto?.kitId != obj.kitId) {
                            kitDto = dataManager.getActiveKit(obj.kitId)?.copy()
                            kitDto?.apiCalls?.clear()
                        }
                        if (kitDto == null) {
                            kitDto = Kit(obj.kitId, dataManager.getKitName(obj.kitId), Status.Red)
                        }
                        kitDto?.apply {  apiCalls.add(obj.copy().apply { expanded = false }) }
                    }
                    is ApiCall -> {
                        obj.copy().apply { expanded = false }
                    }
                    is MessageEvent -> {
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
            EventViewType.valChainTitle.ordinal -> ChainTitleViewHolder(inflater.inflate(R.layout.item_recyclerview_chain_title, parent, false))
            EventViewType.valNext.ordinal -> NextViewHolder(inflater.inflate(R.layout.item_recyclerview_and_then_arrow, parent, false))
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChainTitleViewHolder) {
            val obj = getObjects()[position] as ChainTitle
            holder.title.text = obj.name
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getViewType(obj: Any): EventViewType {
        if (obj is ChainTitle) {
            return EventViewType.valChainTitle
        }
        if (obj is Next) {
            return EventViewType.valNext
        }
        return super.getViewType(obj)
    }

    override fun bindTitleVH(viewHolder: TitleViewHolder, obj: CategoryTitle) {
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
            1 -> chain.first().getChainName() + " to ..."
            else -> chain.first().getChainName() + " to " +
                    if (chain.indexOfLast { it is KitApiCall } > 0) {
                        chain.last { it is KitApiCall }.getChainName(true)
                    } else {
                        chain.last().getChainName(true)
                    }
        }
    }

    private fun shouldSkipNextArrow(previousItem: Event?, nextItem: Event): Boolean {
        return (previousItem?.getDtoType() == EventViewType.valApiCall && nextItem.getDtoType() == EventViewType.valApiCall) ||
                previousItem == null ||
                previousItem is ChainTitle
    }

    fun Event.getChainName(isEnd: Boolean = false): String {
        return when(this) {
            is NetworkRequest, is Kit -> getShortName().capitalize()
            is KitApiCall -> dataManager.getKitName(kitId) + " Kit"
            else -> if (isEnd) " ..." else name
        }
    }

    class Next(override val name: String = "next"): Event()
    inner class NextViewHolder(imageView: View): RecyclerView.ViewHolder(imageView)
}

fun Event.copy(): Event {
    return when(this) {
        is ApiCall -> copy().apply { expanded = false }
        is NetworkRequest -> this.copy().apply { expanded = false }
        is KitApiCall -> this.copy().apply { expanded = false }
        is MessageTable -> MessageTable(name, messages, false)
        is MessageEvent -> MessageEvent(name, body, status, storedTime, bodyExpanded = false, rowId = rowId, id = id)
        is Kit -> this.copy().apply { apiCalls = ArrayList(apiCalls) }
        is StateAllUsers -> StateAllUsers(users)
        is StateCurrentUser -> StateCurrentUser(user)
        is StateStatus -> StateStatus(name, priority, status, fields)
        is StateEvent -> StateEvent(name, priority)
        is CategoryTitle -> CategoryTitle(name, itemType, false, order)
        is ChainListAdapter.Next -> ChainListAdapter.Next()
//        is ChainableEvent -> ChainableEvent(id, name)
        is ChainTitle -> ChainTitle(name)
        else -> throw RuntimeException("event not implemented")
    }
}
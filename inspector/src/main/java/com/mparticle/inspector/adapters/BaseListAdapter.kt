package com.mparticle.inspector.adapters

import android.content.Context
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.Constants.Companion.SESSION_ID
import com.mparticle.inspector.DataManager
import com.mparticle.inspector.R
import com.mparticle.inspector.customviews.JsonTextView
import com.mparticle.shared.events.*
import com.mparticle.inspector.utils.json
import com.mparticle.inspector.utils.setChainClickable
import com.mparticle.inspector.utils.setJsonHandling
import com.mparticle.inspector.utils.visible
import com.mparticle.inspector.viewholders.*
import com.mparticle.shared.EventViewType
import com.mparticle.shared.EventViewType.*
import com.mparticle.shared.ViewControllerManager
import com.mparticle.shared.controllers.BaseController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


abstract class BaseListAdapter(val context: Context, val startTime: Long, val displayCallback: (Int) -> Unit, val dataManager: DataManager, val controller: BaseController? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var objects: List<Event> = ArrayList()
    val inflater = LayoutInflater.from(context)
    var listView: RecyclerView? = null

    val titleStoredMessages = "Database State"
    val titleNetworkRequest = "Network Usage"
    val titleApiCall = "Api Usage"
    val titleKit = "Kit State"
    val titleState = "SDK State"

    init {
        controller?.apply {
            registerAddedListener { position, item ->
                refreshData(events, position, 1, false)
            }
            registerRefreshListener { position, item ->
                refreshData(events, position)
            }
            registerRemovedListener { position, count ->
                refreshData(events, position, count, true)
            }
            registerListUpdatedListener {
                refreshData(events)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val eventType = EventViewType.values().first { it.ordinal == viewType}
        var holder = when (eventType) {
            valTitle -> TitleViewHolder(inflater.inflate(R.layout.item_recyclerview_title, parent, false))
            valNetworkRequest -> NetworkRequestViewHolder(inflater.inflate(R.layout.item_recyclerview_network, parent, false), parent)
            valApiCall -> ApiCallViewHolder(inflater.inflate(R.layout.item_recyclerview_apicall, parent, false), parent)
            valKit -> KitViewHolder(inflater.inflate(R.layout.item_recyclerview_state_status, parent, false), parent)
            valMessage -> MessageQueuedViewHolder(inflater.inflate(R.layout.item_recyclerview_message_queued, parent, false), parent)
            valMessageTable -> MessageTableViewHolder(inflater.inflate(R.layout.item_recyclerview_enumerated, parent, false), parent)
            valStateGeneric -> StateViewHolder(inflater.inflate(R.layout.item_recyclerview_state_generic, parent, false), parent)
            valStateCurrentUser -> StateCurrentUserViewHolder(inflater.inflate(R.layout.item_recyclerview_state_currentuser, parent, false), parent)
            valStateList -> StateListViewHolder(inflater.inflate(R.layout.item_recyclerview_enumerated, parent, false), parent)
            valStateStatus -> StateStatusViewHolder(inflater.inflate(R.layout.item_recyclerview_state_status, parent, false), parent)
            else -> throw RuntimeException("${eventType.name} not yet implemented for recycler view")
        }
        if (eventType != valTitle) {
            holder.itemView.background = ContextCompat.getDrawable(context, R.drawable.list_sub_item)
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).apply {
                var density = context.resources.displayMetrics.density
                bottomMargin = (density * 2 + .5).toInt()
            }
        }
        createAllViewHolders(holder)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = objects[position]
        when (holder) {
            is TitleViewHolder -> bindTitleVH(holder, obj as CategoryTitle)
            is NetworkRequestViewHolder -> bindNetworkViewHolder(holder, obj as NetworkRequest)
            is ApiCallViewHolder -> bindApiViewHolder(holder, obj as ApiCall)
            is KitViewHolder -> bindKitViewHolder(holder, obj as Kit)
            is MessageQueuedViewHolder -> bindMessageQueuedDto(holder, obj as MessageEvent)
            is MessageTableViewHolder -> bindMessageTable(holder, obj as MessageTable)
            is StateCurrentUserViewHolder -> bindStateCurrentUserDto(holder, obj as StateCurrentUser)
            is StateListViewHolder -> when (obj) {
                is StateAllUsers -> bindStateAllUsersDto(holder, obj)
                is StateAllSessions -> bindStateAllSessions(holder, obj)
            }
            is StateStatusViewHolder -> bindStateStatus(holder, obj as StateStatus)
        }
        bindAllViewHolders(holder, obj)
    }

    open fun bindAllViewHolders(holder: RecyclerView.ViewHolder?, event: Event) {}

    open fun createAllViewHolders(viewHolder: RecyclerView.ViewHolder) {}

    override fun getItemCount(): Int {
        return objects.size
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(objects[position]).ordinal
    }

    open fun getViewType(obj: Any) : EventViewType {
        return when (obj) {
            is CategoryTitle -> valTitle
            is NetworkRequest -> valNetworkRequest
            is ApiCall -> valApiCall
            is Kit -> valKit
            is MessageEvent -> valMessage
            is StateCurrentUser -> valStateCurrentUser
            is StateAllUsers, is StateAllSessions -> valStateList
            is StateStatus -> valStateStatus
            is StateEvent -> valStateGeneric
            is MessageTable -> valMessageTable
            else -> throw RuntimeException("${obj.javaClass.simpleName} not implemented for ViewType")
        }
    }

    open fun getItemTitle(obj: Any): String {
        return when (obj) {
            is NetworkRequest -> titleNetworkRequest
            is ApiCall -> titleApiCall
            is Kit -> titleKit
            is MessageEvent -> titleStoredMessages
            is MessageTable -> titleStoredMessages
            is StateCurrentUser -> titleState
            is StateAllUsers -> titleState
            is StateStatus -> titleState
            else -> ""
        }
    }

    open fun bindTitleVH(viewHolder: TitleViewHolder, obj: CategoryTitle)  {
        viewHolder.apply {
            title.text = obj.title
            this.dropDown.isClickable = false
            count.apply {
                visible(!obj.expanded || obj.count == 0)
                text = obj.count.toString()
            }
        }
    }

    protected fun bindNetworkViewHolder(viewHolder: NetworkRequestViewHolder, obj: NetworkRequest) {
        viewHolder.apply {
            type.text = obj.name
            type.setOnClickListener {
                obj.expanded = !obj.expanded
                expand.visible(obj.expanded)
                refreshDataObject(obj)
            }
            expand.visible(obj.expanded)
            url.text = obj.url
            onHasConnections(obj.id) { async ->
                expand.setChainClickable(obj.id, displayCallback)
                if (async) {
                    refreshDataObject(obj)
                }
            }
            bodyExpand.setJsonHandling("request", obj.bodyExpanded, obj.body)
            bodyExpand.setOnClickListener {
                obj.bodyExpanded = !obj.bodyExpanded
                body.visible(obj.bodyExpanded)
                body.bindJson(obj.body)
                refreshDataObject(obj)
            }
            body.visible(obj.bodyExpanded)
            body.bindJson(obj.body)
            responseExpanded.setJsonHandling("response", obj.responseExpanded, obj.responseBody)
            responseExpanded.setOnClickListener {
                obj.responseExpanded = !obj.responseExpanded
                response.visible(obj.responseExpanded)
                response.bindJson(obj.responseBody)
                refreshDataObject(obj)
            }
            responseExpanded.visible(obj.responseBody != null)
            response.visible(obj.responseExpanded)
            response.bindJson(obj.responseBody)
            responseCode.text = obj.responseCode
            time_sent.text = obj.timeSent.getTimeText(startTime)
            status.setStatus(obj.status)
        }
    }

    protected fun bindApiViewHolder(viewHolder: ApiCallViewHolder, obj: ApiCall) {
        viewHolder.apply {
            call_description.text = if (obj is KitApiCall) {
                "${dataManager.getKitName(obj.kitId)}.${obj.name}"
            } else {
                obj.name
            }

            time_sent.text = obj.timeSent.getTimeText(startTime)
            time_sent.visible(obj.status == null)

            status.setStatus(obj.status)
            arguments.removeAllViews()
            obj.methodArguments?.forEach { argument ->
                val argumentView = inflater.inflate(R.layout.item_recyclerview_apicall_argument, expanded, false)
                argumentView.findViewById<TextView>(R.id.type).apply {
                    text = "${argument.className}: "
                }
                argumentView.findViewById<JsonTextView>(R.id.value).apply {
                    if (argument.value is JSONObject) {
                        bindJson(argument.value as JSONObject)
                    } else {
                       setText(argument.value.toString())
                    }
                }
                onHasConnections(argument.id) { async ->
                    argumentView.setChainClickable(argument.id, displayCallback)
                    if (async) {
                        refreshDataObject(obj)
                    }
                }
                arguments.addView(argumentView)
            }
            expanded.visible(obj.expanded)
            this.itemView.setOnClickListener {
                obj.expanded = !obj.expanded
                expanded.visible(obj.expanded)
                refreshData()
            }
        }
    }

    open protected fun bindKitViewHolder(viewHolder: KitViewHolder, obj: Kit) {
        viewHolder.apply {
            kit_name.text = obj.name
            status.setStatus(obj.status)
            expanded.visible(obj.expanded)
            this.itemView.setOnClickListener {
                obj.expanded = !obj.expanded
                expanded.visible(obj.expanded)
                refreshData()
            }
            expanded.removeAllViews()
            var field = inflater.inflate(R.layout.item_recyclerview_keyvalue_horz, expanded, false)
            field.findViewById<TextView>(R.id.key).text = "id: "
            field.findViewById<TextView>(R.id.value).text = obj.kitId.toString()
            expanded.addView(field)
            if (obj.errorMessage != null) {
                var errorField = inflater.inflate(R.layout.item_recyclerview_keyvalue_horz, expanded, false)
                errorField.findViewById<TextView>(R.id.key).text = "error: "
                errorField.findViewById<TextView>(R.id.value).text = obj.errorMessage
                expanded.addView(errorField)
            }
            obj.apiCalls.forEach {
                val apiCallVH= ApiCallViewHolder(inflater.inflate(R.layout.item_recyclerview_apicall, expanded, false))
                bindApiViewHolder(apiCallVH, it)
                expanded.addView(apiCallVH.itemView)
            }
        }
    }

    open protected fun onHasConnections(id: Int?, onConnectable: (Boolean) -> Unit) {
        if (dataManager.hasConnections(id)) {
            onConnectable(false)
        } else {
            dataManager.addCompositesUpdatedListener { _, _ ->
                if (dataManager.hasConnections(id)) {
                    onConnectable(true)
                    true
                } else {
                    false
                }
            }
        }
    }

    protected fun bindMessageQueuedDto(viewHolder: MessageQueuedViewHolder, obj: MessageEvent) {
        viewHolder.apply {
            type.text = "_id: ${obj.rowId}"
            bodyExpanded.visible(obj.bodyExpanded)
            type.setOnClickListener {
                obj.bodyExpanded = !obj.bodyExpanded
                bodyExpanded.visible(obj.bodyExpanded)
                refreshData()
            }
            body.bindJson(obj.body)
            time_sent.text = obj.storedTime.getTimeText(startTime)
        }
    }

    protected fun bindStateCurrentUserDto(viewHolder: StateCurrentUserViewHolder, obj: StateCurrentUser) {
        viewHolder.apply {
            title.text = obj.name
            expanded.visible(obj.expanded)
            title.setOnClickListener {
                obj.expanded = !obj.expanded
                refreshData()
            }
            if (obj.user == null) {
                mpid.text = "-"
                userAttributes.clear()
                userIdentities.clear()
            } else {
                mpid.text = "${obj.user?.id ?: "--"}"
                userAttributes.setOnClickListener {
                    obj.attributesExpanded = !obj.attributesExpanded
                    refreshData()
                }
                userIdentities.setOnClickListener {
                    obj.identitiesExpanded = !obj.identitiesExpanded
                    refreshData()
                }
                userAttributes.bindJson(obj.user?.userAttributes?.json())
                userIdentities.bindJson(obj.user?.userIdentities?.json())
                consentState.bindJson(obj.user?.consentState?.json())
            }
        }
    }

    protected fun bindMessageTable(viewHolder: MessageTableViewHolder?, obj: MessageTable) {
        viewHolder?.apply {
            title.text = "\"${obj.name}\""
            userCount.text = obj.messages.keys.size.toString()
            expanded.visible(obj.bodyExpanded)
            itemView.setOnClickListener {
                obj.bodyExpanded = !obj.bodyExpanded
                expanded.visible(obj.bodyExpanded)
            }
            expanded.removeAllViews()
            obj.messages.entries
                    .sortedByDescending { it.key.storedTime }
                    .forEach { messageExpanded ->
                        val messageQueuedVH = MessageQueuedViewHolder(inflater.inflate(R.layout.item_recyclerview_message_queued, expanded, false))
                        bindMessageQueuedDto(messageQueuedVH, messageExpanded.key)
                        expanded.addView(messageQueuedVH.itemView)
                    }
        }
    }

    protected fun bindStateAllSessions(viewHolder: StateListViewHolder?, obj: StateAllSessions) {
        viewHolder?.apply {
            title.text = obj.name
            userCount.text = obj.sessions.keys.size.toString()
            expanded.visible(obj.expanded)
            itemView.setOnClickListener {
                obj.expanded = !obj.expanded
                expanded.visible(obj.expanded)
            }
            expanded.removeAllViews()
            obj.sessions.entries.forEach { (sessionEvent, isExpanded) ->
                val view = inflater.inflate(R.layout.item_recyclerview_expandable_ll, expanded, false)
                view.findViewById<TextView>(R.id.key).text = "sessionId: "
                view.findViewById<TextView>(R.id.value).text = sessionEvent.fields.get(SESSION_ID).toString()
                val innerExpanded = view.findViewById<ViewGroup>(R.id.expandedArea)
                innerExpanded.visible(isExpanded.value)
                sessionEvent.fields
                        .filter { it.key != SESSION_ID }
                        .forEach {
                            val keyValueViewHolder = KeyValueHorizontal(innerExpanded, context)
                            keyValueViewHolder.key.text = it.key
                            when (it.value) {
                                is JSONObject -> keyValueViewHolder.valueJson.bindJson(it.value as JSONObject)
                                is JSONArray -> keyValueViewHolder.valueJson.bindJson(it.value as JSONArray)
                                else -> keyValueViewHolder.value.text = it.value.toString()
                            }
                            innerExpanded.addView(keyValueViewHolder.itemView)
                        }
                view.setOnClickListener {
                    isExpanded.value = !isExpanded.value
                    innerExpanded.visible(isExpanded.value)
                }

                expanded.addView(view)
            }
        }
    }

    protected fun bindStateAllUsersDto(viewHolder: StateListViewHolder?, obj: StateAllUsers) {
        viewHolder?.apply {
            title.text = obj.name
            userCount.text = obj.users.keys.size.toString()
            expanded.visible(obj.expanded)
            itemView.setOnClickListener {
                obj.expanded = !obj.expanded
                expanded.visible(obj.expanded)
            }
            expanded.removeAllViews()
            obj.users.entries.forEach { userExpanded ->
                val view = inflater.inflate(R.layout.item_recyclerview_expandable_ll, expanded, false)
                view.findViewById<TextView>(R.id.key).text = "mpid: "
                view.findViewById<TextView>(R.id.value).text = userExpanded.key.id.toString()
                val innerExpanded = view.findViewById<ViewGroup>(R.id.expandedArea)
                innerExpanded.visible(userExpanded.value.value)
                HashMap<String, Any>().apply {
                    put("userAttributes: ", userExpanded.key.userAttributes.json())
                    put("userIdentities: ", userExpanded.key.userIdentities.json())
                }.forEach {
                    val keyValueViewHolder = KeyValueHorizontal(innerExpanded, context)
                    keyValueViewHolder.key.text = it.key
                    when (it.value) {
                        is JSONObject -> keyValueViewHolder.valueJson.bindJson(it.value as JSONObject)
                        is JSONArray -> keyValueViewHolder.valueJson.bindJson(it.value as JSONArray)
                        else -> keyValueViewHolder.value.text = it.value.toString()
                    }
                    innerExpanded.addView(keyValueViewHolder.itemView)
                }
                view.setOnClickListener {
                    userExpanded.value.value = !userExpanded.value.value
                    innerExpanded.visible(userExpanded.value.value)
                }

                expanded.addView(view)
            }
        }
    }

    protected fun bindStateStatus(viewHolder: StateStatusViewHolder?, obj: StateStatus) {
        viewHolder?.apply {
            status.setStatus(obj.status())

            title.text = obj.name
            expanded.visible(obj.expanded)
            title.setOnClickListener {
                obj.expanded = !obj.expanded
                refreshData()
            }

            expanded.removeAllViews()
            obj.fields.entries
                    .sortedBy { it.key }
                    .forEach {
                        var field = inflater.inflate(R.layout.item_recyclerview_keyvalue_horz, expanded, false)
                        field.findViewById<TextView>(R.id.key).text = it.key
                        field.findViewById<TextView>(R.id.value).text = when (it.value) {
                            is Function0<*> -> (it.value as Function0<*>).invoke().toString()
                            else -> it.value.toString()
                        }
                        expanded.addView(field)
                    }
        }
    }

    fun Long.getTimeText(startTime: Long) : String {
        val time = Math.abs(this - startTime)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val hours = TimeUnit.MILLISECONDS.toHours(time)
        val builder = StringBuilder();
        if (hours > 0) {
            builder.append("${hours}h:")
        }
        if (minutes> 0) {
            val minutesShort = minutes % 60
            builder.append("${if (minutesShort < 10 && hours > 0) "0" else ""}${minutesShort}m:")
        }
        val secondsShort = seconds % 60
        builder.append("${if (secondsShort < 10 && minutes > 0) "0" else ""}${secondsShort}s")
        return "${if (this >= startTime) "+" else "-"}${builder}"
    }

    internal fun refreshDataObject(obj: Event) {
        fun refresh(obj: Event) {
            val objects = getObjects()
            val index = objects.indexOf(obj)
            if (index >= 0) {
                //sometimes "indexOf" returns true, even though it isn't a referential equality. In that
                //case, manually replace the object
                if (obj != objects[index]) {
                    objects.removeAt(index)
                    objects.add(index, obj)
                }
                refreshData(objects, index)
            } else if ((obj as? ChainableEvent)?.id != null) {
                objects.toList().forEachIndexed { i, namedDto ->
                    if ((namedDto as? ChainableEvent)?.id == obj.id) {
                        objects[i] = obj.copy()
                        refreshData(objects, i)
                    }
                }
            }
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            GlobalScope.launch(Dispatchers.Main) {
                refresh(obj)
            }
        } else {
            refresh(obj)
        }
    }


    protected fun getObjects(): MutableList<Event> {
        return ArrayList(objects)
    }

    internal fun refreshData(objectsList: List<Event> = objects, position: Int? = null, count: Int = 1, removed: Boolean? = null) {
        fun refresh(position: Int? = null, count: Int, removed: Boolean? = null) {
            objects = objectsList
            if (position == null) {
                notifyDataSetChanged()
            } else {
                when (removed) {
                    true -> {
                        notifyItemRangeRemoved(position, count)
                    }
                    false -> {
                        notifyItemRangeInserted(position, count)
                    }
                    null -> {
                        notifyItemChanged(position, count)
                    }
                }
            }
            if (getItemCount() > 0) {
                if (position == 0 && removed == false &&
                        (listView?.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()?: 0 < 1) {
                    listView?.scrollToPosition(0)
                } else {
                    listView?.smoothScrollBy(1, 1);
                }
            }
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            GlobalScope.launch(Dispatchers.Main) {
                refresh(position, count, removed)
            }
        } else {
            refresh(position, count, removed)
        }
    }
}
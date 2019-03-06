package com.mparticle

import android.annotation.SuppressLint
import android.content.Context
import com.mparticle.identity.MParticleUser
import com.mparticle.internal.InternalSession
import com.mparticle.inspector.*
import com.mparticle.inspector.customviews.Status
import com.mparticle.inspector.models.*
import com.mparticle.inspector.utils.broadcast
import com.mparticle.inspector.utils.milliToSecondsString
import com.mparticle.inspector.utils.printClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashSet

class ListenerImplementation private constructor(val context: Context) : ExternalListener {


    var parentsChildren: Map<Int, Set<LinkedHashSet<Identified>>> = HashMap()
    var childrensParents: Map<Int, Set<LinkedHashSet<Identified>>> = HashMap()

    private var parentsChildrenIds: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()
    private var childrensParentIds: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()


    val identifiedDtos: MutableMap<Int, Identified> = HashMap()

    val itemList = ArrayList<Event>()
    var addItemCallback: ((Event) -> Unit)? = null
    var refreshItemCallback: ((Event) -> Unit)? = null
    var onUpdateListeners: MutableList<WeakReference<((Map<Int, Set<LinkedHashSet<Identified>>>, Map<Int, Set<LinkedHashSet<Identified>>>) -> Boolean)?>> = ArrayList()

    val currentUserDto = StateCurrentUser(null)
    val allUsersDto = StateAllUsers(HashMap())
    lateinit var sessionDto: StateStatus
    val previousSessions = StateAllUsers(HashMap())
    var currentSession: InternalSession? = null

    val messageMap = HashMap<Int, MessageQueued>()
    val networkMap = HashMap<Int, NetworkRequest>()
    val activeKits = HashMap<Int, Kit>()

    companion object {
        @SuppressLint("StaticFieldLeak") //application context
        private var instance: ListenerImplementation? = null

        fun instance(context: Context): ListenerImplementation {
            return instance ?: ListenerImplementation(context).also {
                instance = it
            }
        }
    }

    init {
        createAddMParticleState()
        createAddSessionState()

        addItem(currentUserDto)
        addItem(allUsersDto)
    }

    fun attachList(addItemCallback: ((Event) -> Unit)?, refreshItemCallback: ((Event) -> Unit)?) {
        this.addItemCallback = addItemCallback
        this.refreshItemCallback = refreshItemCallback
        if (addItemCallback != null) {
            itemList.forEach { addItemCallback(it) }
            itemList.clear()
        }
    }


    fun getById(id: Int): Identified? {
        return identifiedDtos[id]
    }

    fun removeCompositesUpdatedListener(onUpdate: ((Map<Int, Set<LinkedHashSet<Identified>>>, Map<Int, Set<LinkedHashSet<Identified>>>) -> Unit)?) {
        onUpdateListeners.removeAll { it.get() == onUpdate }
    }

    fun addCompositesUpdatedListener(updateNow: Boolean = false, onUpdate: ((Map<Int, Set<LinkedHashSet<Identified>>>, Map<Int, Set<LinkedHashSet<Identified>>>) -> Boolean)?) {
        onUpdateListeners.add(WeakReference(onUpdate))
        if (updateNow) {
            InternalListenerImpl.instance().updateObjectGraph()
            onUpdateListeners.broadcast { listener ->
                if(listener?.invoke(childrensParents, parentsChildren) == true) {
                    onUpdateListeners.removeAll { it.get() == listener }
                }
            }
        }
    }

    override fun updateComposites(parentsChildren: Map<Int, Set<LinkedHashSet<Int>>>, childrensParents: Map<Int, Set<LinkedHashSet<Int>>>) {
        parentsChildrenIds = parentsChildren
        childrensParentIds = childrensParents
        this.parentsChildren = filterUnusedIds(parentsChildren)
        this.childrensParents = filterUnusedIds(childrensParents)
        onUpdateListeners.broadcast { listener ->
            if (listener?.invoke(this.childrensParents, this.parentsChildren) == true) {
                onUpdateListeners.removeAll { it.get() == listener }
            }
        }
    }

    override fun onMessageCreated(table: String, rowId: Long, messageId: Int, message: JSONObject) {
        val messageDto = MessageQueued(table, message, Status.Yellow, rowId = rowId, id = messageId)
        messageMap.put(messageId, messageDto)
        addItem(messageDto)
    }

    override fun onMessageUploaded(messageId: Int?, networkRequestId: Int?) {
        val message = messageMap.get(messageId)
        message?.status = Status.Green
    }

    override fun onApiMethodCalled(id: Int, apiName: String, objects: List<TrackableObject>, isClientCall: Boolean) {
        objects.map { argument ->
            Argument(argument.obj.javaClass, argument.obj.printClass(), argument.trackingId).copy()
        }.also { arguments ->
            ApiCall(apiName, arguments, System.currentTimeMillis(), id = id)
                    .let { dto ->
                        arguments.forEach { argument ->
                            if (argument.id != null) {
                                identifiedDtos[argument.id] = dto
                            }
                        }
                        if (isClientCall) {
                            addItem(dto)
                        } else {
                            identifiedDtos[id] = dto
                        }
                    }
        }
    }

    override fun onNetworkRequestStarted(id: Int, type: String, url: String, body: JSONObject) {
        val dto = NetworkRequest(type, Status.Yellow, url, body, System.currentTimeMillis(), id = id)
        networkMap.put(id, dto)
        addItem(dto)
    }

    override fun onNetworkRequestFinished(id: Int, url: String?, response: JSONObject?, code: Int) {
        if (networkMap.containsKey(id)) {
            networkMap.get(id)?.apply {
                status = if (code in 200..299 || code == 304) Status.Green else Status.Red
                responseBody = response
                responseCode = code.toString()
            }?.also {
                refreshItem(it)
            }
            networkMap.remove(id)
        }
    }

    private fun refreshItem(dto: Event) {
        refreshItemCallback?.invoke(dto)
    }

    override fun kitFound(kitId: Int, kitName: String) {
        val dto = Kit(kitId, kitName, Status.Red)
        activeKits.put(kitId, dto)
        addItem(dto)
    }

    override fun kitConfigReceived(kitId: Int, kitName: String, configuration: String) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, kitName)
        }
        activeKits.get(kitId)?.apply {
            if (status != Status.Green) {
                status = Status.Yellow
            }
        }
    }

    override fun kitStarted(kitId: Int, kitName: String) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, kitName)
        }
        activeKits.get(kitId)?.status = Status.Green
    }

    override fun kitExcluded(kitId: Int, kitName: String, reason: String?) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, kitName)
        }
        activeKits.get(kitId)?.apply {
            status = Status.Red
            errorMessage = reason
        }
    }

    override fun onKitMethodCalled(id: Int, kitId: Int, apiName: String, usedCall: Boolean?, objects: List<TrackableObject>) {
        objects.map { argument ->
            Argument(argument.obj.javaClass, argument.obj.printClass(), argument.trackingId)
        }.also {
            val status = when (usedCall) {
                true -> Status.Green
                false -> Status.Red
                null -> Status.None
            }
            activeKits.get(kitId)?.apply {
                var apiCallDto = KitApiCall(kitId, apiName, it, System.currentTimeMillis(), status = status, id = id)
                apiCalls.add(apiCallDto)
                apiCallDto.arguments?.forEach { argument ->
                    if (argument.id != null) {
                        identifiedDtos[argument.id] = apiCallDto
                    }
                }
                refreshItem(this)
            }
        }
    }

    override fun onUserIdentified(user: MParticleUser?) {
        allUsersDto.users = MParticle.getInstance()?.Identity()?.users?.run {
            removeAll { it.id == user?.id }
            associate { allUser -> allUser to Mutable(false) }
        } ?: HashMap()
        currentUserDto.user = user
    }

    override fun onSessionUpdated(session: InternalSession?) {
        if (session != currentSession) {

        }
        currentSession = session
        refreshItem(sessionDto)
    }

    private fun addItem(obj: Event) {
        if (obj is Identified) {
            identifiedDtos.put(obj.id, obj)
        }
        GlobalScope.launch(Dispatchers.Main) {
            addItemCallback?.invoke(obj) ?: itemList.add(obj)
        }
    }

    private fun createAddMParticleState() {
        HashMap<String, Any>().apply {
            put("DeviceImei: ", { MParticle.getDeviceImei() ?: "-" })
            put("AndroidId Disabled: ", { MParticle.isAndroidIdDisabled() })
            put("OptOut: ", { MParticle.getInstance()?.optOut })
            put("Install Referrer: ", { MParticle.getInstance()?.installReferrer })
            put("Environment: ", { MParticle.getInstance()?.environment?.name })
            put("Location Tracking", { MParticle.getInstance()?.locationTrackingEnabled })
            put("Auto Tracking", { MParticle.getInstance()?.isAutoTrackingEnabled })
            put("Device Performance", { MParticle.getInstance()?.isDevicePerformanceMetricsDisabled })
            put("Session Timeout", { "${MParticle.getInstance()?.sessionTimeout}s" })
            put("App State", { MParticle.getAppState() })
        }.let {
            StateStatus("MParticle State", 0, { if (MParticle.getInstance() != null) Status.Green else Status.Red }, it)
        }.also {
            addItem(it)
        }
    }

    private fun createAddSessionState() {
        HashMap<String, Any>().apply {
            put("uuid: ", { currentSession?.mSessionID })

            put("foreground time; ", {
                currentSession?.let {
                    (System.currentTimeMillis() - it.mSessionStartTime - it.backgroundTime).milliToSecondsString()
                } ?: "-"
            })
            put("background time: ", { currentSession?.backgroundTime?.milliToSecondsString() ?: "-" })
            put("event count: ", { currentSession?.mEventCount?.toString() ?: "-" })
            put("mpids: ", {
                currentSession?.mpids?.run {
                    if (size == 0) {
                        "-"
                    } else {
                        fold(StringBuilder()) { acc, mpid -> acc.append("$mpid,") }.toString()
                    }
                } ?: "-"
            })
            put("length: ", {
                currentSession?.run {
                    (mLastEventTime - mSessionStartTime).milliToSecondsString()
                } ?: "-"
            })
        }.let {
            StateStatus("Session", 4, { if (currentSession?.isActive == true) Status.Green else Status.Red }, it)
        }.also {
            sessionDto = it
            addItem(it)
        }
    }

    private fun filterUnusedIds(chains: Map<Int, Set<java.util.LinkedHashSet<Int>>>): Map<Int, Set<LinkedHashSet<Identified>>> {
        return chains
                .map { (key, value) ->
                    key to value
                            .map { chain ->
                                chain
                                        .map { getById(it) }
                                        .filterNotNull()
                                        .let { LinkedHashSet(it) }
                            }
                            .filter { it.size > 1 }
                            .toSet()
                }
                .associate { it.first to it.second }
    }

}

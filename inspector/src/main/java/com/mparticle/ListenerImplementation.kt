package com.mparticle

import android.annotation.SuppressLint
import android.content.Context
import com.mparticle.identity.MParticleUser
import com.mparticle.internal.InternalSession
import com.mparticle.inspector.*
import com.mparticle.inspector.customviews.Status
import com.mparticle.inspector.events.*
import com.mparticle.inspector.utils.Mutable
import com.mparticle.inspector.utils.broadcast
import com.mparticle.inspector.utils.milliToSecondsString
import com.mparticle.inspector.utils.printClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashSet

class ListenerImplementation private constructor(val context: Context) : ExternalListener {


    var parentsChildren: Map<Int, Set<LinkedHashSet<ChainableEvent>>> = HashMap()
    var childrensParents: Map<Int, Set<LinkedHashSet<ChainableEvent>>> = HashMap()

    private var parentsChildrenIds: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()
    private var childrensParentIds: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()


    val chainableEventDtos: MutableMap<Int, ChainableEvent> = HashMap()

    val itemList = ArrayList<Event>()
    var addItemCallback: ((Event) -> Unit)? = null
    var refreshItemCallback: ((Event) -> Unit)? = null
    var onUpdateListeners: MutableList<WeakReference<((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Boolean)?>> = ArrayList()

    val mParticleState = createAddMParticleState().apply { priority = 5 }
    val sessionDto = createSessionStatus(null).apply { priority = 4 }
    val currentUserDto = StateCurrentUser(null).apply { priority = 3 }
    val previousSessions = StateAllSessions(HashMap()).apply { priority = 2 }
    val allUsersDto = StateAllUsers(HashMap()).apply { priority = 1 }

    val messageMap = HashMap<Int, MessageEvent>()
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

        val SESSION_ID = "uuid: "
    }

    init {
        addItem(mParticleState)
        addItem(sessionDto)
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


    fun getById(id: Int): ChainableEvent? {
        return chainableEventDtos[id]
    }

    fun removeCompositesUpdatedListener(onUpdate: ((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Unit)?) {
        onUpdateListeners.removeAll { it.get() == onUpdate }
    }

    fun addCompositesUpdatedListener(updateNow: Boolean = false, onUpdate: ((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Boolean)?) {
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
        val messageDto = MessageEvent(table, message, Status.Yellow, rowId = rowId, id = messageId)
        messageMap.put(messageId, messageDto)
        addItem(messageDto)
    }

    override fun onMessageUploaded(messageId: Int?, networkRequestId: Int?) {
        val message = messageMap.get(messageId)
        message?.status = Status.Green
    }

    override fun onApiMethodCalled(id: Int, apiName: String, objects: List<TrackableObject>, isClientCall: Boolean) {
        objects.map { argument ->
            MethodArgument(argument.obj.javaClass, argument.obj.printClass(), argument.trackingId).copy()
        }.also { arguments ->
            ApiCall(apiName, arguments, System.currentTimeMillis(), id = id)
                    .let { dto ->
                        arguments.forEach { argument ->
                            if (argument.id != null) {
                                chainableEventDtos[argument.id] = dto
                            }
                        }
                        if (isClientCall) {
                            addItem(dto)
                        } else {
                            chainableEventDtos[id] = dto
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
            MethodArgument(argument.obj.javaClass, argument.obj.printClass(), argument.trackingId)
        }.also {
            val status = when (usedCall) {
                true -> Status.Green
                false -> Status.Red
                null -> Status.None
            }
            activeKits.get(kitId)?.apply {
                var apiCallDto = KitApiCall(kitId, apiName, it, System.currentTimeMillis(), status = status, id = id)
                apiCalls.add(apiCallDto)
                apiCallDto.methodArguments?.forEach { argument ->
                    if (argument.id != null) {
                        chainableEventDtos[argument.id] = apiCallDto
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
        val currentSession = sessionDto.obj as InternalSession?
        if (session?.mSessionID != currentSession?.mSessionID && currentSession?.isActive == true) {
            createSessionStatus(currentSession).apply {
                fields = fields.entries.associate {
                    it.key to it.value.run {
                        when (this) {
                            is Function0<*> -> invoke() ?: "-"
                            else -> it.value
                        }
                    }
                }.toMutableMap()
            }.also {
                previousSessions.sessions.put(it, Mutable(false))
                if (previousSessions.sessions.size == 1) {
                    addItem(previousSessions)
                } else {
                    refreshItem(previousSessions)
                }
            }
        }

        sessionDto.obj = session
        refreshItem(sessionDto)
    }

    private fun addItem(obj: Event) {
        if (obj is ChainableEvent) {
            chainableEventDtos.put(obj.id, obj)
        }
        GlobalScope.launch(Dispatchers.Main) {
            addItemCallback?.invoke(obj) ?: itemList.add(obj)
        }
    }

    private fun createAddMParticleState(): StateStatus {
        return HashMap<String, Any>().apply {
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
        }
    }

    private fun createSessionStatus(internalSession: InternalSession?): StateStatus {
        val sessionStatus = StateStatus("Session", 4, { if (internalSession?.isActive == true) Status.Green else Status.Red }, obj = internalSession)

        HashMap<String, Any>().apply {
            val session = { sessionStatus.obj as InternalSession? }
            put(SESSION_ID, { session()?.mSessionID })

            put("foreground time; ", {
                session()?.let {
                    (System.currentTimeMillis() - it.mSessionStartTime - it.backgroundTime).milliToSecondsString()
                } ?: "-"
            })
            put("background time: ", { session()?.backgroundTime?.milliToSecondsString() ?: "-" })
            put("event count: ", { session()?.mEventCount.toString() ?: "-" })
            put("mpids: ", {
                session()?.mpids?.run {
                    if (size == 0) {
                        "-"
                    } else {
                        joinToString { it.toString() }
                    }
                } ?: "-"
            })
            put("length: ", {
                session()?.run {
                    (mLastEventTime - mSessionStartTime).milliToSecondsString()
                } ?: "-"
            })
        }.also {
            sessionStatus.fields = it
            sessionStatus.status = { if ((sessionStatus.obj as InternalSession?)?.isActive == true) Status.Green else Status.Red }
        }
        return sessionStatus
    }

    private fun filterUnusedIds(chains: Map<Int, Set<java.util.LinkedHashSet<Int>>>): Map<Int, Set<LinkedHashSet<ChainableEvent>>> {
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

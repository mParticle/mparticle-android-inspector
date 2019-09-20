package com.mparticle.inspector

import com.mparticle.*
import com.mparticle.commerce.Cart
import com.mparticle.identity.IdentityStateListener
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.extensions.getMap
import com.mparticle.shared.events.*
import com.mparticle.inspector.extensions.toObjectArgument
import com.mparticle.inspector.extensions.wrapper
import com.mparticle.internal.InternalSession
import com.mparticle.internal.listeners.GraphManager
import com.mparticle.shared.events.Status
import com.mparticle.shared.utils.Mutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class SdkListenerImpl : GraphManager(), IdentityStateListener {
    private var networkRequests: MutableMap<SdkListener.Endpoint, Int> = HashMap()
    private var identityListenerAdded = false

    private var addItemCallback: ((Event) -> Unit)? = null
    private val itemQueue = ArrayList<Event>()

    private val mParticleState = createAddMParticleState().apply { priority = 5 }
    private val sessionDto = createSessionStatus(null).apply { priority = 4 }
    private val currentUserDto = StateCurrentUser(null).apply { priority = 3 }
    private val previousSessions = StateAllSessions(HashMap()).apply { priority = 2 }
    private val allUsersDto = StateAllUsers(HashMap()).apply { priority = 1 }

    private val messageMap = HashMap<Int, MessageEvent>()
    private val networkMap = HashMap<Int, NetworkRequest>()
    private val activeKits = HashMap<Int, Kit>()

    init {
        addItem(mParticleState)
        addItem(sessionDto)
        addItem(currentUserDto)
        addItem(allUsersDto)
    }

    override fun attachList(addItemCallback: (Event) -> Unit) {
        this.addItemCallback = addItemCallback
        itemQueue.forEach { addItemCallback(it) }
        itemQueue.clear()
    }

    override fun getActiveKit(id: Int): Kit? {
        return activeKits[id]
    }

    override fun onKitApiCalled(kitId: Int, apiName: String, callingApiName: String?, kitManagerApiName: String?, objectList: List<Any?>, used: Boolean) {
        val id = nextId()
        super.onKitApiCalled(id, callingApiName, kitManagerApiName)
        var methodName = apiName
        val split = methodName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        methodName = split[split.size - 1]


        val arguments = objectList.map { it.toObjectArgument(id) }
        val status = when (used) {
            true -> Status.Green
            false -> Status.Red
        }
        activeKits.get(kitId)?.apply {
            val apiCallDto = KitApiCall(kitId, methodName, arguments, System.currentTimeMillis(), status = status, id = id)
            apiCallDto.arguments.forEach { argument ->
                argument.id?.let { id ->
                    chainableEventDtos[id] = apiCallDto
                }
            }
            addItem(apiCallDto)
        }
    }

    override fun onApiCalled(methodName: String, objectList: List<Any>, isExternal: Boolean) {
        tryAddIdentityListener(methodName)
        val id = nextId()
        super.onApiCalled(id, methodName)
        val objectOfInterest = objectList.any { objectIds.containsKey(it) }
        if (isExternal || objectOfInterest) {
            val arguments = objectList.map { obj ->
                val trackingId = track(id, obj)
                obj.toObjectArgument()
            }
//            var objArg = when (obj) {
//                is Cart, is MParticleUser -> obj.toObjectArgument()
//                else -> null
//            }
//            val dto = ApiCall(methodName, arguments, System.currentTimeMillis(), objArg, id = id)
//            arguments.forEach { argument ->
//                argument.id?.let { id ->
//                    chainableEventDtos[id] = dto
//                }
//            }
//            if (isExternal) {
//                addItem(dto)
//            } else {
//                chainableEventDtos[id] = dto
//            }
        }
    }

    override fun onEntityStored(databaseTable: SdkListener.DatabaseTable, primaryKey: Long, jsonObject: JSONObject) {
        super.onEntityStored(databaseTable, primaryKey, jsonObject)
        val tableName = databaseTable.name.toLowerCase()
        var id: Int? = objectIds[tableName + primaryKey]
        if (id == null) {
            id = nextId()
        }
        val messageDto = MessageEvent(tableName, jsonObject.toString(), Status.Yellow, rowId = primaryKey, id = id)
        messageMap.put(id, messageDto)
        addItem(messageDto)
    }

    override fun onNetworkRequestStarted(type: SdkListener.Endpoint, url: String, body: JSONObject) {
        val id = nextId()
        networkRequests[type] = id
        objectIds[body]?.let {
            compositeObjectIds(it, id)
        }
        val typeName = type.name.toLowerCase().capitalize()
        val dto = NetworkRequest(typeName, Status.Yellow, url, body.toString(), System.currentTimeMillis(), id = id)
        networkMap.put(id, dto)
        addItem(dto)
        updateObjectGraph()
    }


    override fun onNetworkRequestFinished(type: SdkListener.Endpoint, url: String, response: JSONObject?, code: Int) {
        val id = networkRequests[type]
        if (id != null) {
            if (networkMap.containsKey(id)) {
                networkMap.get(id)?.apply {
                    status = if (code in 200..299 || code == 304) Status.Green else Status.Red
                    responseBody = response.toString()
                    responseCode = code.toString()
                }?.also {
                    addItem(it)
                }
                networkMap.remove(id)
            }
        }
    }

    override fun onUserIdentified(user: MParticleUser, previousUser: MParticleUser?) {
        allUsersDto.users = MParticle.getInstance()?.Identity()?.users?.run {
            removeAll { it.id == user.id }
            associate { user -> user.wrapper() to Mutable(false) }
        } ?: HashMap()
        currentUserDto.user = user.wrapper()
    }


    override fun onSessionUpdated(internalSession: InternalSession?) {
        val currentSession = sessionDto.obj as InternalSession?
        if (internalSession?.mSessionID != currentSession?.mSessionID && currentSession?.isActive == true) {
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
                    addItem(previousSessions)
                }
            }
        }

        sessionDto.obj = internalSession
        addItem(sessionDto)
    }

    override fun onKitDetected(kitId: Int) {
        val dto = Kit(kitId, getKitName(kitId), Status.Red)
        activeKits.put(kitId, dto)
        addItem(dto)
    }

    override fun onKitConfigReceived(kitId: Int, configuration: JSONObject) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, getKitName(kitId))
        }
        activeKits.get(kitId)?.apply {
            if (status != Status.Green) {
                status = Status.Yellow
            }
        }
    }

    override fun onKitExcluded(kitId: Int, reason: String) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, getKitName(kitId))
        }
        activeKits.get(kitId)?.apply {
            status = Status.Red
            errorMessage = reason
        }
    }

    override fun onKitStarted(kitId: Int) {
        if (!activeKits.containsKey(kitId)) {
            kitFound(kitId, getKitName(kitId))
        }
        activeKits.get(kitId)?.status = Status.Green
    }

    private fun kitFound(kitId: Int, kitName: String) {
        val dto = Kit(kitId, kitName, Status.Red)
        activeKits.put(kitId, dto)
        addItem(dto)
    }


    override fun getKitName(id: Int): String {
        //first try getting the name directly from the kit, using it's "getName()" method
        //this should work everytime, but I'm not totally sure, since we don't want to add
        //kit-base dependency
        val kit = MParticle.getInstance()?.getKitInstance(id)
        if (kit != null) {
            val nameMethod = kit::class.java.getField("getName")
            val kitName = nameMethod.get(kit) as? String
            if (kitName != null) {
                return kitName;
            }
        }
        //if that doesn't work, use the MParticle.ServiceProvider name.
        for (field in MParticle.ServiceProviders::class.java.fields) {
            try {
                if ((field.type == Int::class.javaPrimitiveType || field.type == Int::class.java) && field.getInt(null) == id) {
                    var name = field.name
                    if (name.length > 1) {
                        name = name.substring(0, 1) + name.substring(1).toLowerCase()
                        name = name.replace("_", " ")
                        return name
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return "no name"
    }

    private fun createAddMParticleState(): StateStatus {
        return HashMap<String, Any>().apply {
            put("DeviceImei: ", { MParticle.getDeviceImei() ?: "-" })
            put("AndroidId Disabled: ", { MParticle.isAndroidIdDisabled() })
            put("OptOut: ", { MParticle.getInstance()?.optOut })
            put("Install Referrer: ", { MParticle.getInstance()?.installReferrer })
            put("Environment: ", { MParticle.getInstance()?.environment?.name })
            put("Location Tracking", { MParticle.getInstance()?.isLocationTrackingEnabled })
            put("Auto Tracking", { MParticle.getInstance()?.isAutoTrackingEnabled })
            put("Device Performance", { MParticle.getInstance()?.isDevicePerformanceMetricsDisabled })
            put("Session Timeout", { "${MParticle.getInstance()?.sessionTimeout}s" })
            put("App State", { Access.getAppState() })
        }.let {
            StateStatus("MParticle State", 0, { if (MParticle.getInstance() != null) Status.Green else Status.Red }, it)
        }
    }

    private fun createSessionStatus(internalSession: InternalSession?): StateStatus {
        val sessionStatus = StateStatus("Session", 4, { if (internalSession?.isActive == true) Status.Green else Status.Red }, obj = internalSession)
        (sessionStatus.obj as? InternalSession)?.run {
            val sessionMap = getMap()
            sessionStatus.fields = sessionMap
            sessionStatus.status = { if ((sessionStatus.obj as InternalSession?)?.isActive == true) Status.Green else Status.Red }
        }
        return sessionStatus
    }

    //if the method name is "start()" then it might be an SDK re-start, so add the add the IdentityStateListener
    //again. It's not a big deal if we call addIdentityStateListener() too many times, since it will only
    //keep one instance, but calling too much will generate some unnecessary StackTrace generation
    private fun tryAddIdentityListener(methodName: String? = null) {
        if (methodName?.contains("start") == true) {
            identityListenerAdded = false
        }
        MParticle.getInstance()?.Identity()?.apply {
            if (!identityListenerAdded) {
                identityListenerAdded = true
                addIdentityStateListener(this@SdkListenerImpl)
            }
        }
    }

    override fun addItem(obj: Event) {
        super.addItem(obj)
        GlobalScope.launch(Dispatchers.Main) {
            addItemCallback?.invoke(obj) ?: itemQueue.add(obj)
        }
    }

    companion object {
        private var instance: SdkListenerImpl? = null

        fun instance(): SdkListenerImpl {
            if (instance == null) {
                instance = SdkListenerImpl()
            }
            return instance!!
        }
    }
}
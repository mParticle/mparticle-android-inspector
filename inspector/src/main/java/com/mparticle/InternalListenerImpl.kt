package com.mparticle

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.os.Message
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.TrackableObject
import com.mparticle.inspector.utils.*
import com.mparticle.internal.InternalSession
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

class InternalListenerImpl : com.mparticle.InternalListener() {

    private var parentsChildrenInternal: MutableMap<Int, MutableList<Int>> = ConcurrentHashMap()
    private var childrensParentsInternal: MutableMap<Int, MutableList<Int>> = ConcurrentHashMap()

    var parentsChildren: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()
    var childrensParents: Map<Int, Set<LinkedHashSet<Int>>> = HashMap()

    val apiMap = HashMap<String, Int>()
    val handlerMap = HashMap<String, Message>()

    private var objectIds: MutableMap<Any, Int> = SemiWeakHashMap()
    private var networkRequests: MutableMap<String, Int> = HashMap()

    private val externalObjectIds = TreeSet<Int>()

    internal var i = 0


    internal fun nextId(externalObject: Boolean = true): Int {
        synchronized(this) {
            if (externalObject) {
                externalObjectIds.add(i)
            }
            return i++
        }
    }


    override fun onKitApiCalled(kitId: Int, used: Boolean?, vararg objects: Any?) {
        val stackTrace = Thread.currentThread().stackTrace
        val kitApiCallIndex = stackTrace.indexOfLast { it.className == "com.mparticle.kits.KitIntegration\$Receiver" }
        if (kitApiCallIndex > 0) {
            val apiName = stackTrace[kitApiCallIndex].getApiName()
            onKitApiCalled(stackTrace,apiName, kitId, used, objects)
        }
    }

    override fun onKitApiCalled(apiName: String, kitId: Int, used: Boolean?, vararg objects: Any?) {
        onKitApiCalled(Thread.currentThread().stackTrace, apiName, kitId, used, objects)
    }

    private fun onKitApiCalled(stackTrace: Array<StackTraceElement>, apiName: String, kitId: Int, used: Boolean?, vararg objects: Any?) {
        val objectList = objects.filterNotNull().map { it }
        val id = nextId()

        val trackableObjects = TrackableObject.toTrackableObjects(id, objectList)

        val originalApiCallIndex = stackTrace.indexOfLast { !it.isExternalApiInvocation() }
        if (originalApiCallIndex > -1) {
            apiMap.get(stackTrace[originalApiCallIndex].apiMapName(Thread.currentThread().name))?.also {
                compositeObjectIds(it, id)
            }
        }

        var methodName = apiName
        val split = methodName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        methodName = split[split.size - 1]
        ExternalListenerImpl.instance.onKitMethodCalled(id, kitId, methodName, used, trackableObjects)
    }

    override fun registerHandlerMessage(handlerName: String, msg: Message?, onNewThread: Boolean) {
        if (msg == null) {
            return
        }
        if (onNewThread) {
            handlerMap.put("$handlerName-${Thread.currentThread().name}", msg)
        } else {
            val stackTrace = Thread.currentThread().stackTrace
            val threadName = Thread.currentThread().name
            val apiCallIndex = stackTrace.indexOfFirst { !it.isExternalApiInvocation() && apiMap.containsKey(it.apiMapName(threadName)) }
            if (apiCallIndex < 0) {
                return
            }
            val apiCall = stackTrace[apiCallIndex]
            val childId = apiMap.get(apiCall.apiMapName(threadName))
            if (childId != null && msg.obj != null) {
                val id = nextId()
                objectIds.put(msg.obj, id)
                compositeObjectIds(childId, id)
            }
        }
    }

    override fun onApiCalled(vararg objects: Any) {
        val objectList = objects.map { it }
        val stackTrace = Thread.currentThread().stackTrace
        val apiCallIndex = stackTrace.getFirstLastOf { it.className == javaClass.name }
        val objectOfInterest = objects.any { objectIds.containsKey(it) }
        if (stackTrace.size > apiCallIndex + 3) {
            val apiCall = stackTrace[apiCallIndex + 1]
            val calledFrom = stackTrace[apiCallIndex + 2]
            if (calledFrom.isExternalApiInvocation() || objectOfInterest) {
                val apiName = apiCall.getApiName()
                val id = nextId()
                val trackableObjects = TrackableObject.toTrackableObjects(id, objectList)
                apiMap.put(apiCall.apiMapName(Thread.currentThread().name), id)
                ExternalListenerImpl.instance.onApiMethodCalled(id, apiName, trackableObjects, calledFrom.isExternalApiInvocation())
            }
        }
    }

    override fun compositeObject(childObj: Any?, parentObj: Any?) {
        var child = childObj
        var parent = parentObj
        if (child == null || parent == null) {
            return
        }
        if (parent is Collection<*>) {
            for (p in parent) {
                if (p != null) {
                    compositeObject(child, p)
                }
            }
        }
        child = fixObject(child)
        parent = fixObject(parent)
        if (objectIds.containsKey(child)) {
            var childId: Int? = objectIds[child]
            var parentId: Int? = objectIds[parent]
            if (childId == null) {
                childId = nextId()
                objectIds[child] = childId
            }
            if (parentId == null) {
                parentId = nextId()
                objectIds[parent] = parentId
            }
            //very basic optimization here. If the parent object is just a Cursor (message read from the database), we will not
            //rebuild the connections list. We get a lot of this type, and it will never lead to a new "Chain" connection, so we
            //can safetly forgo the rebuild here w/o risking a miss on the UI side
            compositeObjectIds(childId, parentId, !(parentObj is SQLiteCursor))
        }
    }

    fun registerObject(obj: Any?): Int {
        if (obj == null) {
            return -1
        }
        var id: Int? = objectIds[fixObject(obj)]
        if (id == null) {
            id = nextId()
        }
        registerObject(id, obj)
        return id
    }

    private fun registerObject(id: Int, obj: Any?) {
        if (obj == null) {
            return
        }
        val fixedObj = fixObject(obj)
        objectIds[fixedObj] = id
    }

    private fun fixObject(original: Any): Any {
        var fixed = original
        if (fixed is Cursor) {
            fixed = fixed.toString() + "-" + fixed.position
        }
        if (fixed is MPEvent) {
            fixed = fixed.toString() + "-" + fixed.hashCode()
        }
        return fixed
    }

    internal fun compositeObjectIds(childId: Int, parentId: Int, shouldUpdateGraph: Boolean = true) {
        var children: MutableList<Int>? = parentsChildrenInternal.get(parentId)
        if (children == null) {
            children = ArrayList()
        }
        children.add(childId)
        parentsChildrenInternal[parentId] = children

        var parents: MutableList<Int>? = childrensParentsInternal.get(childId)
        if (parents == null) {
            parents = ArrayList()
        }
        parents.add(parentId)
        childrensParentsInternal[childId] = parents

        if (shouldUpdateGraph) {
            updateObjectGraph()
        }
    }

    override fun onMessageStored(rowId: Long, tableName: String, contentValues: ContentValues) {
        val stackTrace = Thread.currentThread().stackTrace
        val indexOfHandlerCall = stackTrace.indexOfFirst { it.methodName == "handleMessage" } - 1
        if (indexOfHandlerCall > 0) {
            stackTrace[indexOfHandlerCall].run {
                handlerMap.get("${className}-${Thread.currentThread().name}")
            }?.also {
                if (objectIds[it.obj] != null) {
                    compositeObject(it.obj, tableName + rowId)
                } else {
                    compositeObject(contentValues, tableName + rowId)
                }
            }
        }
        val jsonObject = JSONObject()
        for (key in contentValues.keySet()) {
            try {
                var obj = contentValues.get(key)
                if (obj is String) {
                    try {
                        obj = JSONObject(obj)
                    } catch (jse: JSONException) {
                    }

                }
                jsonObject.put(key, obj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        var id: Int? = objectIds[tableName + rowId]
        if (id == null) {
            id = nextId()
        }
        ExternalListenerImpl.instance.onMessageCreated(tableName, rowId, id, jsonObject)
    }

    override fun onNetworkRequestStarted(type: String, url: String, body: JSONObject, vararg objects: Any) {
        val id = nextId()
        networkRequests[url] = id
        networkRequests[body.toString()] = id
        for (obj in objects) {
            objectIds[obj]?.let {
                compositeObjectIds(it, id)
            }
        }
        ExternalListenerImpl.instance.onNetworkRequestStarted(id, type, url, body)
        updateObjectGraph()

    }

    override fun onNetworkRequestFinished(url: String, response: JSONObject, responseCode: Int) {
        val id = networkRequests[url]
        if (id != null) {
            ExternalListenerImpl.instance.onNetworkRequestFinished(id, url, response, responseCode)
        }
    }

    override fun onUserIdentified(user: MParticleUser?) {
        ExternalListenerImpl.instance.onUserIdentified(user)
    }


    override fun onSessionUpdated(internalSession: InternalSession?) {
        ExternalListenerImpl.instance.onSessionUpdated(internalSession)
    }

    override fun kitFound(kitId: Int) {
        ExternalListenerImpl.instance.kitFound(kitId, getKitName(kitId))
    }

    override fun kitConfigReceived(kitId: Int, configuration: String) {
        ExternalListenerImpl.instance.kitConfigReceived(kitId, getKitName(kitId), configuration)
    }

    override fun kitExcluded(kitId: Int, reason: String) {
        ExternalListenerImpl.instance.kitExcluded(kitId, getKitName(kitId), reason)
    }

    override fun kitStarted(kitId: Int) {
        ExternalListenerImpl.instance.kitStarted(kitId, getKitName(kitId))
    }

    fun getKitName(kitId: Int): String {
        for (field in MParticle.ServiceProviders::class.java.fields) {
            try {
                if ((field.type == Int::class.javaPrimitiveType || field.type == Int::class.java) && field.getInt(null) == kitId) {
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

    internal fun updateObjectGraph() {
        val childrensParentsLocal = HashMap<Int, Set<LinkedHashSet<Int>>>()
        val parentsChildrenLocal = HashMap<Int, Set<LinkedHashSet<Int>>>()

        val childrensParentsInternalCopy = HashMap(childrensParentsInternal)
        val parentsChildrenInternalCopy = HashMap(parentsChildrenInternal)

        fun processGraphToConnections(connections: Map<Int, List<Int>>, outputMap: MutableMap<Int, Set<LinkedHashSet<Int>>>) {
            connections.keys.forEach {id ->
                val objectChains = HashSet<LinkedHashSet<Int>>()
                fun chaseConnection(id: Int, currentChain: LinkedHashSet<Int>, masterMap: Map<Int, List<Int>>) {
                    currentChain.add(id)
                    val parents = masterMap.get(id)
                    if (parents?.size ?: 0 > 0) {
                        parents?.forEach { parentId ->
                            if (!currentChain.contains(parentId)) {
                                LinkedHashSet(currentChain)
                                        .let { listCopy ->
                                            chaseConnection(parentId, listCopy, masterMap)
                                        }
                            } else {
                                objectChains.add(currentChain)
                            }
                        }
                    } else {
                        objectChains.add(currentChain)
                    }
                }
                chaseConnection(id, LinkedHashSet(), connections)
                outputMap.put(id, objectChains)
            }
        }

        processGraphToConnections(childrensParentsInternalCopy, childrensParentsLocal)
        processGraphToConnections(parentsChildrenInternalCopy, parentsChildrenLocal)

        childrensParents = childrensParentsLocal
        parentsChildren = parentsChildrenLocal
        ExternalListenerImpl.instance.updateComposites(parentsChildren, childrensParents)
    }

    companion object {
        private var instance: InternalListenerImpl? = null

        fun instance(): InternalListenerImpl {
            if (instance == null) {
                instance = InternalListenerImpl()
            }
            return instance!!
        }

        fun attachToCore() {
            InternalListener.setListener(instance())
        }
    }
}
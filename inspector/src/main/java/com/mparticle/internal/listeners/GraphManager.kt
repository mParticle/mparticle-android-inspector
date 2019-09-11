package com.mparticle.internal.listeners

import android.database.Cursor
import android.os.Message
import com.mparticle.MPEvent
import com.mparticle.SdkListener
import com.mparticle.inspector.DataManager
import com.mparticle.inspector.extensions.apiMapName
import com.mparticle.inspector.extensions.broadcast
import com.mparticle.inspector.extensions.getApiName
import com.mparticle.inspector.extensions.isExternalApiInvocation
import com.mparticle.shared.events.ChainableEvent
import com.mparticle.shared.events.Event
import com.mparticle.inspector.utils.*
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

abstract class GraphManager: SdkListener(), GraphListener, DataManager {

    private var parentsChildrenInternal: MutableMap<Int, MutableList<Int>> = ConcurrentHashMap()
    private var childrensParentsInternal: MutableMap<Int, MutableList<Int>> = ConcurrentHashMap()

    private var parentsChildrenObjects: Map<Int, Set<LinkedHashSet<ChainableEvent>>> = HashMap()
    private var childrensParentsObjects: Map<Int, Set<LinkedHashSet<ChainableEvent>>> = HashMap()

    private var onUpdateListeners: MutableList<WeakReference<((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Boolean)?>> = ArrayList()
    internal val chainableEventDtos: MutableMap<Int, ChainableEvent> = HashMap()

    internal var objectIds: MutableMap<Any, Int> = SemiWeakHashMap()
    private var currentId = 0


    private val handlerMap = HashMap<String, Any?>()
    private val apiMap = HashMap<String, Int>()

    override fun hasConnections(id: Int?): Boolean {
            return id != null && (childrensParentsObjects[id]?.any { it.size > 1 } ?: false ||
                    parentsChildrenObjects[id]?.any { it.size > 1 } ?: false)

    }

    override fun onCompositeObjects(childObj: Any?, parentObj: Any?) {
        var child = childObj
        var parent = parentObj
        if (child == null || parent == null) {
            return
        }
        if (parent is Collection<*>) {
            for (p in parent) {
                if (p != null) {
                    onCompositeObjects(child, p)
                }
            }
        }
        child = fixObject(child)
        parent = fixObject(parent)
        if (objectIds.containsKey(child)) {
            var childId: Int? = objectIds[child]
            var parentId: Int? = objectIds[parent]
            if (childId == null) {
                childId = nextId(child)
            }
            if (parentId == null) {
                parentId = nextId(parent)
            }
            //very basic optimization here. If the parent object is just a Cursor (message read from the database), we will not
            //rebuild the connections list. We get a lot of this type, and it will never lead to a new "Chain" connection, so we
            //can safetly forgo the rebuild here w/o risking a miss on the UI side
            compositeObjectIds(childId, parentId, !(parentObj is Cursor))
        }
    }

    override fun onThreadMessage(handlerName: String, msg: Message?, onNewThread: Boolean, stackTrace: Array<out StackTraceElement>?) {
        if (msg == null) {
            return
        }
        if (onNewThread) {
            handlerMap.put("${handlerName}_${Thread.currentThread().name}", msg.obj)
        } else {
            if (stackTrace == null) {
                return
            }
            val threadName = Thread.currentThread().name
            stackTrace.firstOrNull() {element ->
                !element.isExternalApiInvocation() && apiMap.containsKey(element.getApiName().apiMapName(threadName))
            }?.run {
                apiMap.get(getApiName().apiMapName(threadName))?.let { childId ->
                    if (msg.obj != null) {
                        val id = nextId(msg.obj)
                        compositeObjectIds(childId, id)
                    }
                }
            }
        }
    }

    override fun onEntityStored(databaseTable: SdkListener.DatabaseTable, primaryKey: Long, jsonObject: JSONObject) {
        val tableName = databaseTable.name.toLowerCase()
        val threadName = Thread.currentThread().name
        val stackTrace = Thread.currentThread().stackTrace
        val indexOfHandlerCall = stackTrace.indexOfFirst { it.methodName == "handleMessage" } - 1
        if (indexOfHandlerCall > 0) {
            stackTrace[indexOfHandlerCall].run {
                handlerMap.get("${className}_$threadName")
            }?.also {
                if (objectIds[it] != null) {
                    onCompositeObjects(it, tableName + primaryKey)
                }
            }
        }
    }

    fun removeCompositesUpdatedListener(onUpdate: ((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Unit)?) {
        onUpdateListeners.removeAll { it.get() == onUpdate }
    }

    override fun addCompositesUpdatedListener(updateNow: Boolean, onUpdate: ((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Boolean)?) {
        onUpdateListeners.add(WeakReference(onUpdate))
        if (updateNow) {
            updateObjectGraph()
        }
    }

    fun broadcastComposites() {
        onUpdateListeners.broadcast { listener ->
            if(listener?.invoke(childrensParentsObjects, parentsChildrenObjects) == true) {
                onUpdateListeners.removeAll { it.get() == listener }
            }
        }
    }

    override fun getById(id: Int): ChainableEvent? {
        return chainableEventDtos[id]
    }


    protected fun onKitApiCalled(id: Int, callingApiName: String?, kitManagerApiName: String?) {
        val threadName = Thread.currentThread().name
        apiMap[callingApiName?.apiMapName(threadName)]?.also {
            compositeObjectIds(it, id)
        }
        if (kitManagerApiName != null) {
            apiMap[kitManagerApiName.apiMapName(threadName)] = id
        }
    }

    protected fun onApiCalled(id: Int, methodName: String) {
        apiMap[methodName.apiMapName(Thread.currentThread().name)] = id
    }

    internal fun nextId(externalObject: Any? = null): Int {
        synchronized(this) {
            if (externalObject != null) {
                objectIds[externalObject] = currentId
            }
            return currentId++
        }
    }

    fun registerObject(obj: Any?): Int {
        if (obj == null) {
            return -1
        }
        val fixedObject = fixObject(obj);
        var id: Int? = objectIds[fixedObject]
        if (id == null) {
            id = nextId(fixedObject)
        }
        return id
    }

    private fun fixObject(original: Any): Any {
        var fixed = when (original) {
            is Cursor ->original.toString() + "-" + original.position
            is MPEvent -> System.identityHashCode(original)
            else -> original
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

        childrensParentsObjects = filterUnusedIds(childrensParentsLocal)
        parentsChildrenObjects = filterUnusedIds(parentsChildrenLocal)

        broadcastComposites()
    }

    open protected fun addItem(obj: Event) {
        if (obj is ChainableEvent) {
            chainableEventDtos.put(obj.id, obj)
        }
    }

    protected fun track(parentId: Int, obj: Any): Int {
        val id = registerObject(obj)
        compositeObjectIds(parentId, id)
        compositeObjectIds(id, parentId)
        return id
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

    data class TrackableObject(val obj: Any, val trackingId: Int);

}
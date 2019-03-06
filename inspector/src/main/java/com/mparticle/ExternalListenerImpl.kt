package com.mparticle

import com.mparticle.identity.MParticleUser
import com.mparticle.internal.InternalSession
import com.mparticle.inspector.TrackableObject
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.HashSet
import java.util.LinkedHashSet

class ExternalListenerImpl : ExternalListener {

    private constructor()

    companion object {
        val instance = ExternalListenerImpl()
    }

    internal var listeners: MutableSet<WeakReference<ExternalListener>> = HashSet()
    override fun onUserIdentified(user: MParticleUser?) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onUserIdentified(user)
            }
        })
    }

    override fun onApiMethodCalled(id: Int?, name: String?, trackableObjects: List<TrackableObject>?, isExternal: Boolean?) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onApiMethodCalled(id, name, trackableObjects, isExternal)
            }
        })
    }

    override fun onMessageCreated(table: String, rowId: Long, messageId: Int?, message: JSONObject) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onMessageCreated(table, rowId, messageId, message)
            }
        })
    }

    override fun onMessageUploaded(messageId: Int?, networkRequestId: Int?) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onMessageUploaded(messageId, networkRequestId)
            }
        })
    }

    override fun onNetworkRequestStarted(id: Int?, type: String, url: String, body: JSONObject) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onNetworkRequestStarted(id, type, url, body)
            }
        })
    }

    override fun onNetworkRequestFinished(id: Int?, url: String, response: JSONObject, responseCode: Int) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onNetworkRequestFinished(id, url, response, responseCode)
            }
        })
    }

    override fun kitFound(kitId: Int, kitName: String) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.kitFound(kitId, kitName)
            }
        })
    }

    override fun kitConfigReceived(kitId: Int, kitName: String, configuration: String) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.kitConfigReceived(kitId, kitName, configuration)
            }
        })
    }

    override fun kitExcluded(kitId: Int, kitName: String, reason: String) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.kitExcluded(kitId, kitName, reason)
            }
        })
    }

    override fun kitStarted(kitId: Int, kitName: String) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.kitStarted(kitId, kitName)
            }
        })
    }

    override fun onSessionUpdated(session: InternalSession?) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onSessionUpdated(session)
            }
        })
    }

    override fun updateComposites(parentsChildren: Map<Int, Set<LinkedHashSet<Int>>>?, childrensParents: Map<Int, Set<LinkedHashSet<Int>>>?) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.updateComposites(parentsChildren, childrensParents)
            }
        })
    }

    override fun onKitMethodCalled(id: Int?, kitId: Int?, name: String, used: Boolean?, objects: List<TrackableObject>) {
        broadcast(object : Runner {
            override fun run(listener: ExternalListener) {
                listener.onKitMethodCalled(id, kitId, name, used, objects)
            }
        })
    }

    private fun broadcast(runner: Runner) {
        for (listenerRef in listeners) {
            val listener = listenerRef.get()
            if (listener != null) {
                runner.run(listener)
            }
        }
    }

    internal fun addListener(internalListener: ExternalListener) {
        listeners.add(WeakReference(internalListener))
    }

    internal interface Runner {
        fun run(listener: ExternalListener)
    }

    interface ApiCallRunner {
        fun run(id: Int, methodName: String, trackableObjects: List<TrackableObject>)
    }
}

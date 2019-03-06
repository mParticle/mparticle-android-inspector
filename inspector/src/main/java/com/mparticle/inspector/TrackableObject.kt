package com.mparticle.inspector

import com.mparticle.InternalListenerImpl

class TrackableObject(val obj: Any, val trackingId: Int) {

    companion object {
        fun toTrackableObjects(parentId: Int, objects: List<Any>): List<TrackableObject> {
            val trackableObjects = ArrayList<TrackableObject>()
            for (obj in objects) {
                val id = InternalListenerImpl.instance().registerObject(obj)
                trackableObjects.add(TrackableObject(obj, id))
                InternalListenerImpl.instance().compositeObjectIds(parentId, id)
                InternalListenerImpl.instance().compositeObjectIds(id, parentId)
            }
            return trackableObjects
        }
    }
}
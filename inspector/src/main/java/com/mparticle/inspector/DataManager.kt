package com.mparticle.inspector

import com.mparticle.inspector.events.ChainableEvent
import com.mparticle.inspector.events.Event
import com.mparticle.inspector.events.Kit
import java.util.LinkedHashSet

interface DataManager {
    fun getActiveKit(id: Int): Kit?
    fun getKitName(id: Int): String
    fun attachList(addItemCallback: ((Event) -> Unit)?, refreshItemCallback: ((Event) -> Unit)?)

    fun addCompositesUpdatedListener(updateNow: Boolean = false, onUpdate: ((Map<Int, Set<LinkedHashSet<ChainableEvent>>>, Map<Int, Set<LinkedHashSet<ChainableEvent>>>) -> Boolean)?)
    fun getById(id: Int): ChainableEvent?
    fun hasConnections(id: Int?): Boolean

}
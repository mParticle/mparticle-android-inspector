package com.mparticle.shared

import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.events.Event
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamControllerTests {
    lateinit var controller: StreamController
    lateinit var manager: ViewControllerManager


    @BeforeTest
    fun setup() {
        manager = ViewControllerManager()
        controller = manager.streamController
    }

    @Test
    fun testEmpty() {
        assertEquals(0, controller.eventCountLimit)
    }

    @Test
    fun testOrder() {
        val events = ArrayList<Event>()
        for (i in 0..200) {
            val timestamp = PlatformApis().getTimestamp() - 100 * i
            events.add(Event("event $i", timestamp))
        }
        events.shuffle()
        events.forEach { manager.addEvent(it) }

        assertEquals(controller.eventCountLimit, events.size)

        controller.getItems()
                .foldRight(Long.MIN_VALUE) { event, acc ->
                    assertTrue { event.createdTime > acc }
                    event.createdTime
                }
    }
}
package com.mparticle.shared

import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.events.ApiCall
import com.mparticle.shared.events.Event
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamControllerTests {
    lateinit var controller: StreamController
    lateinit var manager: ViewControllerManager
    val random = Random.Default


    @BeforeTest
    fun setup() {
        manager = ViewControllerManager()
        controller = manager.streamController
    }

    @Test
    fun testEmpty() {
        assertEquals(expected = 100, actual = controller.eventCountLimit)
    }

    @Test
    fun testOrder() {
        val events = ArrayList<Event>()
        for (i in 0..205) {
            val timestamp = random.nextLong()
            events.add(ApiCall("event $i", null, timestamp, iddd = 1234))
        }
        events.shuffle()
        events.forEach { manager.addEvent(it) }

        assertEquals(controller.eventCountLimit, controller.getEvents().size)

        controller.getEvents()
                .foldRight(Long.MIN_VALUE) { event, acc ->
                    assertTrue { event.createdTime <= acc }
                    event.createdTime
                }
    }
}
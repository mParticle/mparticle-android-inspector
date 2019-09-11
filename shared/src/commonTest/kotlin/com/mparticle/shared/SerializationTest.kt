package com.mparticle.shared

import com.mparticle.shared.events.ApiCall
import kotlin.test.Test
import kotlin.test.fail

class SerializationTest {

    @Test
    fun testSimple() {
        val event = ApiCall(endpoint = "test",id = 1, arguments = listOf(), timeSent = 100)
        val serializer = Serializer()
        val serialized = serializer.serialize(listOf(event))
        val deserialized = serializer.deserialize(serialized)
        fail(serialized)
    }
}
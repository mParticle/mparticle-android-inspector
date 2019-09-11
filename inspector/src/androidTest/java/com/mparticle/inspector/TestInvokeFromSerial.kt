package com.mparticle.inpector

import android.content.ContextWrapper
import android.os.Looper
import android.support.test.InstrumentationRegistry
import com.mparticle.MParticle
import com.mparticle.identity.IdentityApi
import com.mparticle.identity.IdentityStateListener
import com.mparticle.identity.MParticleUser
import com.mparticle.inspector.BaseAbstractTest
import com.mparticle.inspector.Importer
import com.mparticle.inspector.test.R
import com.mparticle.internal.Logger
import com.mparticle.shared.Serializer
import com.mparticle.shared.events.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Modifier

class TestInvokeFromSerial: BaseAbstractTest() {
    val serializer = Serializer()

    @Before
    fun beforeTest() {
        Looper.prepare()
    }


    @Test
    fun testTest() {
        val importer = Importer(InstrumentationRegistry.getContext())

//        val fileContents = File("sample.txt").readText()
        val fileContents = mContext.resources.openRawResource(R.raw.sample).bufferedReader().readLines().joinToString { it }
        val events = importer.deserializeEvents(fileContents)
        val apiEvents = events.filter { it is ApiCall && !(it is KitApiCall) } as List<ApiCall>

        importer.executeApiEvents(apiEvents)
    }

}


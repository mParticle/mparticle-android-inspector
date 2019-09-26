package com.mparticle.inspector

import android.content.Context
import com.mparticle.mock.MockApplication
import com.mparticle.mock.MockContext
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class InspectorTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testStart() {
        Inspector.mpUtilityWrapper.isAppDebugable = { true }
        Inspector.mpUtilityWrapper.getProp = { "package.name" }

        val application = InspectorMockApplication(MutablePackageContext())
        makeUnstartable()

        //should start when app is debuggable
        Inspector.mpUtilityWrapper.isAppDebugable = { true }
        Inspector.startWidget(application)
        assertNotNull(Inspector.getInstance())

        makeUnstartable()

        Inspector.mpUtilityWrapper.getProp = { "package.name" }
        (application.baseContext as MutablePackageContext).packageName = "package.name"

        Inspector.startWidget(application)
        assertNotNull(Inspector.getInstance())

    }

    private fun makeUnstartable() {
        val application = MockApplication(MockContext())

        Inspector.mpUtilityWrapper.isAppDebugable = { false }
        Inspector.mpUtilityWrapper.getProp = { "" }

        //should not start
        Inspector.getInstance()?.stop()
        Inspector.startWidget(application)
        assertNull(Inspector.getInstance())
    }

    class InspectorMockApplication(val context: MockContext): MockApplication(context) {
        override fun unregisterActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {

        }

        override fun getBaseContext(): Context {
            return context
        }
    }

    class MutablePackageContext: MockContext() {
        private var packageName = ""

        fun setPackageName(packageName: String) {
            this.packageName = packageName
        }

        override fun getPackageName(): String {
            return packageName
        }
    }
}
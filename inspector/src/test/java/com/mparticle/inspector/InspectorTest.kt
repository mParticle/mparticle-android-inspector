package com.mparticle.inspector

import android.app.Application
import android.content.Context
import com.mparticle.internal.MPUtility
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
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
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
    @PrepareForTest(MPUtility::class)
    fun testStart() {
        val application = InspectorMockApplication(MutablePackageContext())
        makeUnstartable()

        //should start when app is debuggable
        Mockito.`when`(MPUtility.isAppDebuggable(Mockito.any(Context::class.java))).thenReturn(true)
        Inspector.startWidget(application)
        assertNotNull(Inspector.getInstance())

        makeUnstartable()

        Mockito.`when`(MPUtility.getProp(Mockito.anyString())).thenReturn("package.name")
        (application.baseContext as MutablePackageContext).packageName = "package.name"

        Inspector.startWidget(application)
        assertNotNull(Inspector.getInstance())

    }

    private fun makeUnstartable() {
        val application = MockApplication(MockContext())

        PowerMockito.mockStatic(MPUtility::class.java)
        Mockito.`when`(MPUtility.isAppDebuggable(Mockito.any(Context::class.java))).thenReturn(false)
        Mockito.`when`(MPUtility.getProp(Mockito.anyString())).thenReturn("")

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
package com.mparticle.inspector

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.github.tbouron.shakedetector.library.ShakeDetector
import com.mparticle.MParticle
import com.mparticle.inspector.utils.Utils
import com.mparticle.internal.MPUtility
import java.lang.ref.WeakReference

class Inspector private constructor(val application: Application, showOnStartup: Boolean) {

    var widget: InspectorView? = null
    private var callbacks: Callbacks? = null
    var dimensions: Dimensions = Dimensions()
    var visible = false;
    var currentActivity = WeakReference<Activity>(null)
    val startTime = System.currentTimeMillis()
    val sdkListener = SdkListenerImpl()

    companion object {
        private var instance: Inspector? = null

        fun startWidget(appication: Application, showOnStartup: Boolean = false) {
            if (canStart(appication)) {
                if (instance == null) {
                    instance = Inspector(appication, showOnStartup)
                }
            }
        }

        fun getInstance(): Inspector? {
            return instance
        }

        private fun canStart(context: Context): Boolean {
            return MPUtility.isAppDebuggable(context) ||
                    context.packageName == MPUtility.getProp("debug.mparticle.listener")
        }
    }

    init {
        callbacks = Callbacks()
        MParticle.addListener(application, sdkListener)
        MParticle.getInstance()?.Identity()?.apply {
            addIdentityStateListener(sdkListener)
            currentUser?.let { sdkListener.onUserIdentified(it) }
        }
        widget = InspectorView(application, sdkListener, startTime)
        application.registerActivityLifecycleCallbacks(callbacks)
        if (showOnStartup || Utils.isSimulator()) {
            show()
        }
        try {
        ShakeDetector.create(application)  {
            if (!visible) {
                show()
            }
        }
        } catch (_: Exception) {}
    }

    fun stop() {
        widget?.detach()
        application.unregisterActivityLifecycleCallbacks(callbacks)
        instance = null
        visible = false;
    }

    fun show() {
        visible = true
        currentActivity.get()?.let {
            widget?.attach(it)
        }
    }

    internal fun hide() {
        visible = false
        widget?.detach()
    }

    private inner class Callbacks() : Application.ActivityLifecycleCallbacks {

        override fun onActivityPaused(activity: Activity?) {
            widget?.detach()
            if (activity === currentActivity.get()) {
                currentActivity = WeakReference<Activity>(null)
            }
        }

        override fun onActivityResumed(activity: Activity?) {
            if (activity === currentActivity.get()) {
                return
            }
            if (activity != null) {
                if (visible) {
                    if (widget == null) {
                        widget = InspectorView(activity, sdkListener, startTime)
                    }
                    widget?.attach(activity)
                }
                currentActivity = WeakReference(activity)
            }
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }
    }

    class Dimensions() {
        var set = false

        var leftMargin: Int = 0
        var rightMargin: Int = 0
        var topMargin: Int = 0
        var bottomMargin: Int = 0


        fun update(layoutParams: FrameLayout.LayoutParams) {
            set = true
            leftMargin = layoutParams.leftMargin
            rightMargin = layoutParams.rightMargin
            topMargin = layoutParams.topMargin
            bottomMargin = layoutParams.bottomMargin
        }

        fun applyTo(layoutParams: FrameLayout.LayoutParams) {
            set = true
            layoutParams.leftMargin = leftMargin
            layoutParams.rightMargin = rightMargin
            layoutParams.topMargin = topMargin
            layoutParams.bottomMargin = bottomMargin
        }
    }
}
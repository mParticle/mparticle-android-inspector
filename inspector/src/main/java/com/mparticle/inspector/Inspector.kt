package com.mparticle.inspector

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.FrameLayout
import com.github.tbouron.shakedetector.library.ShakeDetector
import com.mparticle.ExternalListenerImpl
import com.mparticle.InternalListenerImpl
import com.mparticle.ListenerImplementation
import com.mparticle.inspector.utils.Utils
import java.lang.ref.WeakReference

class Inspector private constructor(val application: Application, showOnStartup: Boolean) {

    var widget: InspectorView? = null
    var listener: ListenerImplementation = ListenerImplementation.instance(application.applicationContext)
    private var callbacks: Callbacks? = null
    var dimensions: Dimensions = Dimensions()
    var visible = false;
    var currentActivity = WeakReference<Activity>(null)
    val startTime = System.currentTimeMillis()

    companion object {
        private var instance: Inspector? = null

        fun startWidget(appication: Application, showOnStartup: Boolean = false): Inspector {
            if (instance == null) {
                instance = Inspector(appication, showOnStartup)
            }
            return instance!!
        }

        fun getInstance(): Inspector? {
            return instance
        }
    }

    init {
        ExternalListenerImpl.instance.addListener(listener)
        callbacks = Callbacks()
        InternalListenerImpl.attachToCore()
        widget = InspectorView(application, listener, startTime)
        application.registerActivityLifecycleCallbacks(callbacks)
        if (showOnStartup || Utils.isSimulator()) {
            show()
        }
        ShakeDetector.create(application)  {
            if (!visible) {
                show()
            }
        }
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
                        widget = InspectorView(activity, listener, startTime)
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
package com.mparticle.inspector.customviews

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.mparticle.inspector.R
import com.mparticle.inspector.Inspector
import android.widget.EditText
import com.mparticle.inspector.Exporter


class ResizeDraggableLayout(context: Context, attrs: AttributeSet? = null): RelativeLayout(context, attrs) {

    private var draggingValue = false
    private var dragging: Boolean
        get() = draggingValue
        set(value) {
            setTransparent(value)
            draggingValue = value
        }
    var yDelta = 0
    var logo: View? = null
    var dragger: View? = null
    var top = false
    var parentHeight = 0
    var topMargin = 0
    var bottomMargin = 0
    var startingX = 0f

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        logo = findViewById<View>(R.id.logo).apply {
            setOnClickListener { showEmailPrompt() }
        }
        dragger = findViewById(R.id.dragger)


        dragger?.setOnTouchListener { _, event ->
            var parentLayoutParams = (this@ResizeDraggableLayout.layoutParams as FrameLayout.LayoutParams)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (top) {
                        yDelta = rootView.height - event.rawY.toInt() - parentLayoutParams.bottomMargin
                    } else {
                        yDelta = event.rawY.toInt() - parentLayoutParams.topMargin
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (top) {
                        val screenHeight = rootView.height
                        parentLayoutParams.bottomMargin = screenHeight - event.rawY.toInt() - yDelta
                        if (parentLayoutParams.bottomMargin < 0) {
                            parentLayoutParams.bottomMargin = 0
                        }

                    } else {
                        parentLayoutParams.topMargin = event.rawY.toInt() - yDelta
                        if (parentLayoutParams.topMargin < 0) {
                            parentLayoutParams.topMargin = 0
                        }
                    }


                    Inspector.getInstance()?.dimensions?.update(parentLayoutParams)
                    this@ResizeDraggableLayout.requestLayout()
                }
            }
            true
        }

        setOnLongClickListener {
            dragging = true
            var parentLayoutParams = (this@ResizeDraggableLayout.layoutParams as FrameLayout.LayoutParams)
            topMargin = parentLayoutParams.topMargin
            bottomMargin = parentLayoutParams.bottomMargin
            true
        }


        setOnTouchListener { _, event ->
            parentHeight = (this@ResizeDraggableLayout.parent as View).height
            if (gestureDetector.onTouchEvent(event)) {
                true
            } else {
                fun processTouch(): Boolean {
                    var parentLayoutParams = (layoutParams as FrameLayout.LayoutParams)

                    when (event.action) {
                        MotionEvent.ACTION_UP -> {
                            if (dragging) {
                                if (shouldHide(event.rawX)) {
                                    return true
                                }
                                dragging = false
                                val viewHeight = parentHeight - yDelta
                                val halfWayPoint = viewHeight / 2
                                if ((event.rawY.toInt() - yDelta) > halfWayPoint) {
                                    pinToBottom(parentLayoutParams)
                                } else {
                                    pinToTop(parentLayoutParams)
                                }
                                Inspector.getInstance()?.dimensions?.update(parentLayoutParams)
                                requestLayout()
                                return true
                            } else {
                                return false
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (dragging) {
                                parentLayoutParams.bottomMargin = parentHeight - event.rawY.toInt() - yDelta
                                parentLayoutParams.topMargin = event.rawY.toInt() - yDelta

                                if (parentLayoutParams.rightMargin < 0) {
                                    var ratioOff = (parentLayoutParams.rightMargin * -1).toDouble() / width.toDouble()
                                    if (ratioOff > .5) {
                                        Inspector.getInstance()?.hide()
                                    }
                                }
                                this@ResizeDraggableLayout.requestLayout()
                                return true
                            } else {
                                return false
                            }
                        }
                        MotionEvent.ACTION_DOWN -> {
                            yDelta = event.rawY.toInt() - parentLayoutParams.topMargin
                            startingX = event.rawX
                            return false
                        }
                        else -> return false

                    }
                }

                var result = processTouch()
                result
            }
        }
        isClickable = true
    }

    val gestureDetector = GestureDetector(context, GestureListener(this))

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (gestureDetector.onTouchEvent(event)) {
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

    fun setTransparent(transparent: Boolean) {
        if (transparent) {
            alpha = .25f
        } else {
            alpha = 1f
        }
    }

    fun pinToTop(parentLayoutParams: FrameLayout.LayoutParams) {
        (dragger?.layoutParams as RelativeLayout.LayoutParams).apply {
            removeRule(ALIGN_PARENT_TOP)
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
        }
        parentLayoutParams.apply {
            bottomMargin =  if (top) this@ResizeDraggableLayout.bottomMargin else this@ResizeDraggableLayout.topMargin
            if (bottomMargin < 0) {
                bottomMargin = 0
            }
            topMargin = 0
        }
        top = true
    }

    fun pinToBottom(parentLayoutParams: FrameLayout.LayoutParams) {
        (dragger?.layoutParams as RelativeLayout.LayoutParams).apply {
            removeRule(ALIGN_PARENT_BOTTOM)
            addRule(ALIGN_PARENT_TOP, TRUE)
        }
        parentLayoutParams.apply {
            topMargin = if (top) this@ResizeDraggableLayout.bottomMargin else this@ResizeDraggableLayout.topMargin
            if (topMargin < 0) {
                topMargin = 0
            }
            bottomMargin = 0
        }
        top = false
    }


    internal fun updateLogoSize() {
        (logo?.layoutParams as? RelativeLayout.LayoutParams)?.apply {
            leftMargin = (this@ResizeDraggableLayout.width * .35).toInt()
            rightMargin = (this@ResizeDraggableLayout.width * .35).toInt()
            topMargin = (this@ResizeDraggableLayout.height * .05).toInt()
        }
    }

    internal fun showEmailPrompt() {
        val input = EditText(context)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Export")
        alertDialog.setView(input)
        alertDialog.setMessage("Enter email for export log")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { _, _ ->
            Exporter(Inspector.getInstance()?.widget?.objectMap!!).email(input.text.toString())
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
        (input.layoutParams as FrameLayout.LayoutParams).apply {
            var density = context.resources.displayMetrics.density
            leftMargin = (density * 8 + .5).toInt()
            rightMargin = (density * 8 + .5).toInt()
        }
        input.requestLayout()
    }

    private fun shouldHide(currentX: Float): Boolean {
        val xTravel = Math.abs(startingX - currentX)
        if (xTravel > width / 2) {
            Inspector.getInstance()?.hide()
            return true
        }
        return false
    }

    class GestureListener(val view: View) : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            view.visibility = View.GONE
            Handler().postDelayed({
                view.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate()
                            .alpha(1f)
                            .setDuration(750L)
                            .start()

                }
            }, 3000);
            return true;
        }
    }

    override fun requestLayout() {
        updateLogoSize()
        super.requestLayout()
    }
}
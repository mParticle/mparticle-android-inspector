package com.mparticle.inspector.customviews

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mparticle.inspector.R

import com.mparticle.inspector.customviews.Status.*
import com.mparticle.inspector.utils.visible

enum class Status {
    Red,
    Green,
    Yellow,
    None
}
class StatusView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    var statusLight : View

    init {
        LayoutInflater.from(context).inflate(R.layout.status_light, this, true)
        statusLight = findViewById(R.id.status_indicator)
    }

    fun setStatus(status: Status?) {
        statusLight.visible(status != null && status != Status.None)
        when(status) {
            Green -> statusLight.setBackgroundResource(R.drawable.green_circle)
            Yellow -> statusLight.setBackgroundResource(R.drawable.yellow_circle)
            Red -> statusLight.setBackgroundResource(R.drawable.red_circle)
            else -> statusLight.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }
}
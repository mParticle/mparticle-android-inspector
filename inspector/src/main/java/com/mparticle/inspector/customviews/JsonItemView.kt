package com.mparticle.inspector.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.R

class JsonItemView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(mContext, attrs, defStyleAttr) {
    private lateinit var keyTextView: TextView
    private lateinit var valueTextView: TextView
    private lateinit var icon: ImageView
    private var textSize = 12

    var value: String
        get() = valueTextView.text.toString()
        set(text) {
            valueTextView.visibility = View.VISIBLE
            valueTextView.text = text
        }

    init {
        initView()
    }

    private fun initView() {
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_json, this, true)

        keyTextView = findViewById(R.id.key)
        valueTextView = findViewById(R.id.value)
        icon = findViewById(R.id.icon)
    }

    fun setTextSize(textSizeDp: Float) {
        textSize = textSizeDp.toInt()

        keyTextView.textSize = textSize.toFloat()
        valueTextView.textSize = textSize.toFloat()
        valueTextView.setTextColor(JsonViewAdapter.textColor)

        val textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeDp, resources.displayMetrics).toInt()

        val layoutParams = icon.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = textSize
        layoutParams.width = textSize
        layoutParams.topMargin = textSize / 5

        icon.layoutParams = layoutParams
    }

    fun setRightColor(color: Int) {
        valueTextView.setTextColor(color)
    }

    fun hideLeft() {
        keyTextView.visibility = View.GONE
    }

    fun setKey(text: String) {
        keyTextView.visibility = View.VISIBLE
        keyTextView.text = text
    }

    fun hideIcon() {
        icon.visibility = View.GONE
    }

    fun showIcon(isPlus: Boolean) {
        icon.visibility = View.VISIBLE
        icon.setImageResource(if (isPlus) R.drawable.plus else R.drawable.minus)
        icon.contentDescription = resources.getString(if (isPlus) R.string.plus else R.string.minus)
    }

    fun setIconClickListener(listener: View.OnClickListener) {
        icon.setOnClickListener(listener)
    }

    fun addViewNoInvalidate(child: View) {
        var params: ViewGroup.LayoutParams? = child.layoutParams
        if (params == null) {
            params = generateDefaultLayoutParams()
            if (params == null) {
                throw IllegalArgumentException("generateDefaultLayoutParams() cannot return null")
            }
        }
        addViewInLayout(child, -1, params)
    }
}
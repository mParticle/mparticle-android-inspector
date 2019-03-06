package com.mparticle.inspector.customviews

import android.widget.TextView
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet

class EllipsizedTextView(context: Context, attributes: AttributeSet): TextView(context, attributes) {

    var isEllipsize = true
    var originalText: String
    var ellipsizedText: String = ""

    init {
        originalText = text.toString()
        setEllipsized()
        setOnClickListener {
            isEllipsize = !isEllipsize
            setEllipsized()
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (text.toString() != ellipsizedText) {
            originalText = text.toString()
        }
        setEllipsized()
    }

    private fun setEllipsized() {
        if (isEllipsize) {
            var lengthToRemain = 4
            if (originalText.length >= 4) {
                if (ellipsizedText.startsWith("-")) {
                    lengthToRemain++
                }
                ellipsizedText = "${originalText.substring(0, lengthToRemain + 1)}..."
            } else {
                ellipsizedText = originalText
            }
            ellipsize = TextUtils.TruncateAt.MIDDLE
            setSingleLine(true)
            if (text.toString() != ellipsizedText) {
                text = ellipsizedText
            }
        } else {
            ellipsize = null
            setSingleLine(false)
            if (text.toString() != originalText ) {
                text = originalText
            }

        }
        requestLayout()
    }
}
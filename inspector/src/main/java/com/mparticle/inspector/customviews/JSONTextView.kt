package com.mparticle.inspector.customviews

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.R
import com.yuyh.jsonviewer.library.JsonRecyclerView
import org.json.JSONObject

class JSONTextView(context: Context, attributes: AttributeSet) : JsonRecyclerView(context, attributes) {

    var setGone = false

    init {
        layoutManager = LockedScrollLinearLayoutManager(context)
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
        setTextSize(14f)
    }

    fun setTextColor(color: Int) {
        setKeyColor(color)
        setValueTextColor(color)
        setValueBooleanColor(color)
        setValueNumberColor(color)
        setValueUrlColor(color)
        setValueNullColor(color)
        setBracesColor(color)
    }

    fun setText(text: String) {
        adapter = TextViewAdapter(text)
    }

    override fun bindJson(obj: JSONObject?) {
        if (obj == null || obj.length() == 0) {
            setText("-")
        } else if (visibility == View.VISIBLE) {
            super.bindJson(obj)
        }
    }

    fun clear() {
        adapter = null
    }

    inner class TextViewAdapter(val text: String) : RecyclerView.Adapter<TextViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
            return TextViewHolder(LayoutInflater.from(context).inflate(R.layout.textview, parent, false) as TextView)
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
            holder.textView.text = text
        }

    }

    class TextViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

    inner class LockedScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

        override fun canScrollVertically(): Boolean {
            return false
        }
    }
}
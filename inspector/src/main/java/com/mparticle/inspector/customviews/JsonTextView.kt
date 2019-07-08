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
import org.json.JSONArray
import org.json.JSONObject

class JsonTextView(context: Context, attributes: AttributeSet) : RecyclerView(context, attributes) {

    var setGone = false
    private var mAdapter: JsonViewAdapter? = null

    init {
        layoutManager = LockedScrollLinearLayoutManager(context)
        JsonViewAdapter.textColor = ContextCompat.getColor(context, android.R.color.black)
        JsonViewAdapter.textSize = 14f
    }

    fun setText(text: String) {
        adapter = TextViewAdapter(text)
    }

    fun bindJson(obj: JSONObject?) {
        if (obj == null || obj.length() == 0) {
            setText("-")
        } else if (visibility == View.VISIBLE) {
            mAdapter = null
            mAdapter = JsonViewAdapter(obj)
            adapter = mAdapter
        }
    }

    fun bindJson(jsonStr: String?) {
        //minimum length for a valid json w/more than 0 items is 5 ("{a:b}"
        if (jsonStr == null || jsonStr.length < 5) {
            setText("-")
        } else {
            mAdapter = null
            mAdapter = JsonViewAdapter(jsonStr)
            adapter = mAdapter
        }
    }

    fun bindJson(array: JSONArray) {
        mAdapter = null
        mAdapter = JsonViewAdapter(array)
        adapter = mAdapter
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
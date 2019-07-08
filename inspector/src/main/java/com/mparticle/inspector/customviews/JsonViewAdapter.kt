package com.mparticle.inspector.customviews

import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

class JsonViewAdapter: RecyclerView.Adapter<JsonViewAdapter.JsonItemViewHolder> {


    private var mJSONObject: JSONObject? = null
    private var mJSONArray: JSONArray? = null

    constructor(jsonStr: String) {
        var obj: Any? = null
        try {
            obj = JSONTokener(jsonStr).nextValue()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        if (obj is JSONObject) {
            mJSONObject = obj
        } else if (obj is JSONArray) {
            mJSONArray = obj
        } else {
            throw IllegalArgumentException("jsonStr is illegal.")
        }
    }

    constructor(jsonObject: JSONObject) {
        this.mJSONObject = jsonObject
        if (mJSONObject == null) {
            throw IllegalArgumentException("jsonObject can not be null.")
        }
    }

    constructor(jsonArray: JSONArray) {
        this.mJSONArray = jsonArray
        if (mJSONArray == null) {
            throw IllegalArgumentException("jsonArray can not be null.")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JsonItemViewHolder {
        return JsonItemViewHolder(JsonItemView(parent.context))
    }

    override fun onBindViewHolder(holder: JsonItemViewHolder, position: Int) {
        val itemView = holder.itemView as JsonItemView
        itemView.setTextSize(textSize)
        itemView.setRightColor(textColor)
        if (mJSONObject != null) {
            if (position == 0) {
                itemView.hideLeft()
                itemView.hideIcon()
                itemView.value = "{"
                return
            } else if (position == itemCount - 1) {
                itemView.hideLeft()
                itemView.hideIcon()
                itemView.value = "}"
                return
            } else if (mJSONObject!!.names() == null) {
                return
            }

            val key = mJSONObject!!.names().optString(position - 1) // 遍历key
            val value = mJSONObject!!.opt(key)
            if (position < itemCount - 2) {
                handleJsonObject(key, value, itemView, true, 1)
            } else {
                handleJsonObject(key, value, itemView, false, 1) // 最后一组，结尾不需要逗号
            }
        }

        if (mJSONArray != null) {
            if (position == 0) {
                itemView.hideLeft()
                itemView.hideIcon()
                itemView.value = "["
                return
            } else if (position == itemCount - 1) {
                itemView.hideLeft()
                itemView.hideIcon()
                itemView.value = "]"
                return
            }

            val value = mJSONArray!!.opt(position - 1) // 遍历array
            if (position < itemCount - 2) {
                handleJsonArray(value, itemView, true, 1)
            } else {
                handleJsonArray(value, itemView, false, 1) // 最后一组，结尾不需要逗号
            }
        }
    }

    override fun getItemCount(): Int {
        return mJSONObject?.let { jsonObject ->
            if (jsonObject.names() != null) {
                jsonObject.names().length() + 2
            } else {
                2
            }
        } ?: mJSONArray?.let {
            it.length() + 2
        } ?: 0
    }

    private fun handleJsonObject(key: String, value: Any, itemView: JsonItemView, appendComma: Boolean, hierarchy: Int) {
        val keyBuilder = SpannableStringBuilder(getHierarchyStr(hierarchy))
                .append("\"")
                .append(key)
                .append("\"")
                .append(":")
        keyBuilder.apply {
            setSpan(ForegroundColorSpan(textColor), 0, keyBuilder.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(textColor), keyBuilder.length - 1, keyBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        itemView.setKey(keyBuilder.toString())
        handleValue(value, itemView, appendComma, hierarchy)
    }

    private fun handleJsonArray(value: Any, itemView: JsonItemView, appendComma: Boolean, hierarchy: Int) {
        itemView.setKey(SpannableStringBuilder(getHierarchyStr(hierarchy)).toString())

        handleValue(value, itemView, appendComma, hierarchy)
    }

    private fun handleValue(value: Any?, itemView: JsonItemView, appendComma: Boolean, hierarchy: Int) {
        val valueBuilder = SpannableStringBuilder()
        when (value) {
            is Number, is Boolean -> valueBuilder.apply {
                append(value.toString())
                setSpan(ForegroundColorSpan(textColor), 0, valueBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            is JSONObject -> {
                itemView.showIcon(true)
                valueBuilder
                        .append("Object{...}")
                        .setSpan(ForegroundColorSpan(textColor), 0, valueBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                itemView.setIconClickListener(JsonItemClickListener(value, itemView, appendComma, hierarchy + 1))
            }
            is JSONArray -> {
                itemView.showIcon(true)
                valueBuilder.append("Array[").append(value.length().toString()).append("]")
                val len = valueBuilder.length
                valueBuilder.setSpan(ForegroundColorSpan(textColor), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                valueBuilder.setSpan(ForegroundColorSpan(textColor), 6, len - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                valueBuilder.setSpan(ForegroundColorSpan(textColor), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                itemView.setIconClickListener(JsonItemClickListener(value, itemView, appendComma, hierarchy + 1))
            }
            is String -> {
                itemView.hideIcon()
                valueBuilder.append("\"").append(value.toString()).append("\"")
                valueBuilder.setSpan(ForegroundColorSpan(textColor), 0, valueBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            valueBuilder.isEmpty(), value == null -> {
                itemView.hideIcon()
                valueBuilder.append("null")
                valueBuilder.setSpan(ForegroundColorSpan(textColor), 0, valueBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        if (appendComma) {
            valueBuilder.append(",")
        }
        itemView.value = valueBuilder.toString()
    }

    internal inner class JsonItemClickListener(private val value: Any?, private val itemView: JsonItemView, private val appendComma: Boolean, private val hierarchy: Int) : View.OnClickListener {

        private var isCollapsed = true
        private val isJsonArray: Boolean

        init {
            this.isJsonArray = value != null && value is JSONArray
        }

        override fun onClick(view: View) {
            if (itemView.childCount == 1) {
                isCollapsed = false
                itemView.showIcon(false)
                itemView.tag = itemView.value
                itemView.value = if (isJsonArray) "[" else "{"
                val array = if (isJsonArray) value as JSONArray? else (value as JSONObject).names()
                var i = 0
                while (array != null && i < array.length()) {
                    val childItemView = JsonItemView(itemView.context)
                    childItemView.setTextSize(textSize)
                    childItemView.setRightColor(textColor)
                    val childValue = array.opt(i)
                    if (isJsonArray) {
                        handleJsonArray(childValue, childItemView, i < array.length() - 1, hierarchy)
                    } else {
                        handleJsonObject(childValue as String, (value as JSONObject).opt(childValue), childItemView, i < array.length() - 1, hierarchy)
                    }
                    itemView.addViewNoInvalidate(childItemView)
                    i++
                }

                val childItemView = JsonItemView(itemView.context)
                childItemView.setTextSize(textSize)
                childItemView.setRightColor(textColor)
                val builder = StringBuilder(getHierarchyStr(hierarchy - 1))
                builder.append(if (isJsonArray) "]" else "}").append(if (appendComma) "," else "")
                childItemView.value = builder.toString()
                itemView.addViewNoInvalidate(childItemView)
                itemView.requestLayout()
                itemView.invalidate()
            } else {
                val temp = itemView.value
                itemView.value = itemView.tag.toString()
                itemView.tag = temp
                itemView.showIcon(!isCollapsed)
                for (i in 1 until itemView.childCount) {
                    itemView.getChildAt(i).visibility = if (isCollapsed) View.VISIBLE else View.GONE
                }
                isCollapsed = !isCollapsed
            }
        }
    }

    private fun getHierarchyStr(hierarchy: Int): String {
        val levelStr = StringBuilder()
        for (i in 0 until hierarchy) {
            levelStr.append("      ")
        }
        return levelStr.toString()
    }

    inner class JsonItemViewHolder(itemView: JsonItemView) : RecyclerView.ViewHolder(itemView) {

        init {
            setIsRecyclable(false)
        }
    }

    companion object {
        var textColor: Int = 0
        var textSize = 12f
    }
}

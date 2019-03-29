package com.mparticle.inspector.viewholders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.customviews.JsonTextView
import com.mparticle.inspector.R


class KeyValueHorizontal(parent: ViewGroup?, context: Context, imageView: View = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_keyvalue_horz, parent, false),
                         val key: TextView = imageView.findViewById(R.id.key),
                         val value: TextView = imageView.findViewById(R.id.value),
                         val valueJson: JsonTextView = imageView.findViewById(R.id.valueJson)): RecyclerView.ViewHolder(imageView)
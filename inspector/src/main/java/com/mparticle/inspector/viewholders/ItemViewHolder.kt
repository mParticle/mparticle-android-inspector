package com.mparticle.inspector.viewholders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.extensions.visible
import com.mparticle.inspector.R

open class ItemViewHolder(imageView: View, parent : ViewGroup?, var titleView: TextView? = null): RecyclerView.ViewHolder(imageView.let {
    if (parent != null) {
        LayoutInflater.from(imageView.context)
                .inflate(R.layout.item_recyclerview_titled_ited, parent, false).apply {
                    findViewById<ViewGroup>(R.id.content).addView(imageView)
                }
    } else {
        it
    }
}) {
    init {
        if (parent != null) {
            titleView = itemView.findViewById(R.id.title)
            titleView?.visible(false)
        }
    }
}
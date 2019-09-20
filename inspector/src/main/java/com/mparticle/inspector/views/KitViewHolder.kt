package com.mparticle.inspector.views

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.R
import com.mparticle.inspector.customviews.StatusView

class KitViewHolder(imageView: View, parent: ViewGroup?,
                    val kit_name: TextView = imageView.findViewById(R.id.title),
                    val status: StatusView = imageView.findViewById(R.id.status),
                    val expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent) {

}
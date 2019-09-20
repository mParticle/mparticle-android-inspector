package com.mparticle.inspector.views

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.R

open class StateViewHolder(imageView: View, parent: ViewGroup?,
                           val title: TextView = imageView.findViewById(R.id.title),
                           var expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent)
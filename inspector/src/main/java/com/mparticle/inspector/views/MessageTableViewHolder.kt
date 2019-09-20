package com.mparticle.inspector.views

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.R

class MessageTableViewHolder(imageView: View, parent: ViewGroup?,
                             val title: TextView = imageView.findViewById(R.id.title),
                             val userCount: TextView = imageView.findViewById(R.id.count),
                             var expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent)
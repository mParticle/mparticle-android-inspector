package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.R

class StateListViewHolder(imageView: View, parent: ViewGroup?,
                          val userCount: TextView = imageView.findViewById(R.id.count)): StateViewHolder(imageView, parent)
package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.customviews.JSONTextView
import com.mparticle.inspector.R

class MessageQueuedViewHolder(imageView: View, parent: ViewGroup? = null,
                              val type: TextView = imageView.findViewById(R.id.type),
                              val bodyExpanded: LinearLayout = imageView.findViewById(R.id.expanded),
                              val body: JSONTextView = imageView.findViewById(R.id.body),
                              val time_sent: TextView = imageView.findViewById(R.id.time_sent)) : ItemViewHolder(imageView, parent)
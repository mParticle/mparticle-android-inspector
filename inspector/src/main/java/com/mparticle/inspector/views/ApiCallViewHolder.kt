package com.mparticle.inspector.views

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.R
import com.mparticle.inspector.customviews.StatusView

class ApiCallViewHolder(imageView: View, parent: ViewGroup? = null,
                        val call_description: TextView = imageView.findViewById(R.id.call_description),
                        val time_sent: TextView = imageView.findViewById(R.id.time_sent),
                        val expanded: LinearLayout = imageView.findViewById(R.id.expand),
                        val arguments: LinearLayout = imageView.findViewById(R.id.methodArguments),
                        val status: StatusView = imageView.findViewById(R.id.status)) : ItemViewHolder(imageView, parent)
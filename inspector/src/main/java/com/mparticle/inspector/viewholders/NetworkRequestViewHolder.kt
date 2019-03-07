package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.customviews.JSONTextView
import com.mparticle.inspector.customviews.StatusView
import com.mparticle.inspector.R


class NetworkRequestViewHolder(imageView: View, parent: ViewGroup?,
                               val type: TextView = imageView.findViewById(R.id.type),
                               val expand: View = imageView.findViewById(R.id.expand),
                               val url: TextView = imageView.findViewById(R.id.url),
                               val bodyExpand: TextView = imageView.findViewById(R.id.body_expand),
                               val body: JSONTextView = imageView.findViewById(R.id.requestBody),
                               val responseExpanded: TextView = imageView.findViewById(R.id.response_expand),
                               val response: JSONTextView = imageView.findViewById(R.id.responseBody),
                               val responseCode: TextView = imageView.findViewById(R.id.response_code),
                               val time_sent: TextView = imageView.findViewById(R.id.time_sent),
                               val status: StatusView = imageView.findViewById(R.id.status)): ItemViewHolder(imageView, parent) {

}
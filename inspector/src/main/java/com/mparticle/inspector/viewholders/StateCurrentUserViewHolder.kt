package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.customviews.JsonTextView
import com.mparticle.inspector.R

class StateCurrentUserViewHolder(imageView: View, parent: ViewGroup?,
                                 val mpid: TextView = imageView.findViewById(R.id.mpid),
                                 val userAttributes: JsonTextView = imageView.findViewById(R.id.userAttributes),
                                 val userIdentities: JsonTextView = imageView.findViewById(R.id.userIdentities),
                                 val consentState: JsonTextView = imageView.findViewById(R.id.consentState)): StateViewHolder(imageView, parent)
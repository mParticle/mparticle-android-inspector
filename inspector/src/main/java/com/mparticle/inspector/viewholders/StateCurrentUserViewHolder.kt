package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mparticle.inspector.customviews.JSONTextView
import com.mparticle.inspector.R

class StateCurrentUserViewHolder(imageView: View, parent: ViewGroup?,
                                 val mpid: TextView = imageView.findViewById(R.id.mpid),
                                 val userAttributes: JSONTextView = imageView.findViewById(R.id.userAttributes),
                                 val userIdentities: JSONTextView = imageView.findViewById(R.id.userIdentities),
                                 val consentState: JSONTextView = imageView.findViewById(R.id.consentState)): StateViewHolder(imageView, parent)
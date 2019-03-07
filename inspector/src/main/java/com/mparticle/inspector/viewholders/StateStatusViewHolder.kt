package com.mparticle.inspector.viewholders

import android.view.View
import android.view.ViewGroup
import com.mparticle.inspector.customviews.StatusView
import com.mparticle.inspector.R

class StateStatusViewHolder(imageView: View, parent: ViewGroup?,
                            var status: StatusView = imageView.findViewById(R.id.status)): StateViewHolder(imageView, parent)
package com.mparticle.inspector.views

import android.view.View
import android.view.ViewGroup
import com.mparticle.inspector.R
import com.mparticle.inspector.customviews.StatusView

class StateStatusViewHolder(imageView: View, parent: ViewGroup?,
                            var status: StatusView = imageView.findViewById(R.id.status)): StateViewHolder(imageView, parent)
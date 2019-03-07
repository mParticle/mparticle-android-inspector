package com.mparticle.inspector.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mparticle.inspector.R

class ChainTitleViewHolder(imageView: View,
                           val title: TextView = imageView.findViewById(R.id.title)): RecyclerView.ViewHolder(imageView) {

}
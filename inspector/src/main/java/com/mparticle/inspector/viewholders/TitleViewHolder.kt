package com.mparticle.inspector.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.mparticle.inspector.R

class TitleViewHolder(imageView: View,
                      val title: TextView = imageView.findViewById(R.id.title),
                      val count: TextView = imageView.findViewById(R.id.count),
                      val dropDown: Button = imageView.findViewById(R.id.dropdown)) : RecyclerView.ViewHolder(imageView) {

}
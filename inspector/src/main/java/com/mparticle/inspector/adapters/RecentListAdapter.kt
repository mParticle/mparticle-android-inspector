package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.mparticle.inspector.*
import com.mparticle.shared.events.*
import com.mparticle.inspector.views.*
import com.mparticle.inspector.extensions.visible
import com.mparticle.shared.controllers.BaseController

class RecentListAdapter(context: Context, dataManager: DataManager, controller: BaseController, displayCallback: (Int) -> Unit, startTime: Long): BaseListAdapter(context, startTime, displayCallback, dataManager, controller) {

    override fun createAllViewHolders(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.titleView?.visible(true)
        }
    }

    override fun bindAllViewHolders(holder: RecyclerView.ViewHolder?, event: Event) {
        if (holder is ItemViewHolder) {
            holder.titleView?.text = event.getShortName()
        }
    }
}
package com.mparticle.inspector.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.mparticle.inspector.DataManager
import com.mparticle.inspector.utils.visible
import com.mparticle.inspector.viewholders.ItemViewHolder
import com.mparticle.inspector.viewholders.TitleViewHolder
import com.mparticle.shared.ViewControllerManager
import com.mparticle.shared.controllers.BaseController
import com.mparticle.shared.events.CategoryTitle
import com.mparticle.shared.events.Event

class CategorizedListAdapter(context: Context, dataManager: DataManager, controller: BaseController, displayCallback: (Int) -> Unit, startTime: Long): BaseListAdapter(context, startTime, displayCallback, dataManager, controller) {

    override fun createAllViewHolders(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.titleView?.visible(false)
        }
        if (!(viewHolder is TitleViewHolder)) {
            (viewHolder.itemView.layoutParams as RecyclerView.LayoutParams).apply {
                var density = context.resources.displayMetrics.density
                leftMargin = (density * 12 + .5).toInt()
            }
        }
    }

    override fun bindTitleVH(viewHolder: TitleViewHolder, obj: CategoryTitle) {
        super.bindTitleVH(viewHolder, obj)
        viewHolder.itemView.setOnClickListener {
                obj.toggleExpanded()
        }
    }

}

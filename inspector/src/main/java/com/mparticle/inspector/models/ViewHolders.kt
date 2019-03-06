package com.mparticle.inspector.models

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.mparticle.inspector.customviews.JSONTextView
import com.mparticle.inspector.customviews.StatusView
import com.mparticle.inspector.R
import com.mparticle.inspector.utils.visible

class TitleViewHolder(imageView: View,
                      val title: TextView = imageView.findViewById(R.id.title),
                      val count: TextView = imageView.findViewById(R.id.count),
                      val dropDown: Button = imageView.findViewById(R.id.dropdown)) : RecyclerView.ViewHolder(imageView) {

}

class ChainTitleViewHolder(imageView: View,
                           val title: TextView = imageView.findViewById(R.id.title)): RecyclerView.ViewHolder(imageView) {

}

open class ItemViewHolder(imageView: View, parent : ViewGroup?, var titleView: TextView? = null): RecyclerView.ViewHolder(imageView.let {
    if (parent != null) {
        LayoutInflater
                .from(imageView.context)
                .inflate(R.layout.item_recyclerview_titled_ited, parent, false).apply {
                    findViewById<ViewGroup>(R.id.content).addView(imageView)
                }
    } else {
        it
    }
}) {
    init {
        if (parent != null) {
            titleView = itemView.findViewById(R.id.title)
            titleView?.visible(false)
        }
    }
}

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

class ApiCallViewHolder(imageView: View, parent: ViewGroup? = null,
                        val call_description: TextView = imageView.findViewById(R.id.call_description),
                        val time_sent: TextView = imageView.findViewById(R.id.time_sent),
                        val expanded: LinearLayout = imageView.findViewById(R.id.expand),
                        val arguments: LinearLayout = imageView.findViewById(R.id.arguments),
                        val status: StatusView = imageView.findViewById(R.id.status)) : ItemViewHolder(imageView, parent)

class KitViewHolder(imageView: View, parent: ViewGroup?,
                    val kit_name: TextView = imageView.findViewById(R.id.title),
                    val status: StatusView = imageView.findViewById(R.id.status),
                    val expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent) {

}

class MessageQueuedViewHolder(imageView: View, parent: ViewGroup? = null,
                              val type: TextView = imageView.findViewById(R.id.type),
                              val bodyExpanded: LinearLayout = imageView.findViewById(R.id.expanded),
                              val body: JSONTextView = imageView.findViewById(R.id.body),
                              val time_sent: TextView = imageView.findViewById(R.id.time_sent)) : ItemViewHolder(imageView, parent)

class MessageTableViewHolder(imageView: View, parent: ViewGroup?,
                             val title: TextView = imageView.findViewById(R.id.title),
                             val userCount: TextView = imageView.findViewById(R.id.count),
                             var expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent)

open class StateViewHolder(imageView: View, parent: ViewGroup?,
                           val title: TextView = imageView.findViewById(R.id.title),
                           var expanded: LinearLayout = imageView.findViewById(R.id.expanded)) : ItemViewHolder(imageView, parent)

class StateCurrentUserViewHolder(imageView: View, parent: ViewGroup?,
                                 val mpid: TextView = imageView.findViewById(R.id.mpid),
                                 val userAttributes: JSONTextView = imageView.findViewById(R.id.userAttributes),
                                 val userIdentities: JSONTextView = imageView.findViewById(R.id.userIdentities),
                                 val consentState: JSONTextView = imageView.findViewById(R.id.consentState)): StateViewHolder(imageView, parent)

class StateAllUsersViewHolder(imageView: View, parent: ViewGroup?,
                              val userCount: TextView = imageView.findViewById(R.id.count)): StateViewHolder(imageView, parent)


class StateStatusViewHolder(imageView: View, parent: ViewGroup?,
                            var status: StatusView = imageView.findViewById(R.id.status)): StateViewHolder(imageView, parent)


class KeyValueHorizontal(parent: ViewGroup?, context: Context, imageView: View = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_keyvalue_horz, parent, false),
                         val key: TextView = imageView.findViewById(R.id.key),
                         val value: TextView = imageView.findViewById(R.id.value),
                         val valueJson: JSONTextView = imageView.findViewById(R.id.valueJson)): RecyclerView.ViewHolder(imageView)
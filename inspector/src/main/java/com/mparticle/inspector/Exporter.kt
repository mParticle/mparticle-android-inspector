package com.mparticle.inspector

import com.mparticle.inspector.utils.printClass
import org.json.JSONObject
import java.util.*
import android.content.Intent
import com.mparticle.shared.events.Event
import org.json.JSONArray


class Exporter(events: List<Event>) {

    var contents: String = ""

    init {
        events.sortedBy { it.createdTime }
                .map { it.serialize() }
                .fold(JSONArray()) { acc, s ->
                    acc.put(s)
                }
                .also {
                    contents = it.toString(4)
                }
    }

    fun email(emailAddress: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inspector Log: ${Inspector.getInstance()?.application?.packageName}")
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents)
        Inspector.getInstance()?.currentActivity?.get()?.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"))
    }

    fun Event.serialize(): String {
        return printClass(this.javaClass.name, JSONObject()).toString(4)
    }

}
package com.mparticle.inspector

import com.mparticle.inspector.utils.printClass
import org.json.JSONObject
import java.util.*
import android.content.Intent
import com.mparticle.inspector.events.Event
import org.json.JSONArray


class Exporter(objects: Map<EventViewType, Collection<Event>>) {

    var contents: String = ""

    init {
        objects.flattenValues()
                .sortedBy { it.createdTime }
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

fun <T> Map<*, Collection<T>>.flattenValues(): List<T> {
    return values.fold(ArrayList()) { acc, list ->
        acc.apply { addAll(list)}
    }
}
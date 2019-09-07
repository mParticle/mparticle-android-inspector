package com.mparticle.inspector

import android.content.Intent
import com.mparticle.inspector.utils.getMap
import com.mparticle.internal.InternalSession
import com.mparticle.shared.Serializer
import com.mparticle.shared.events.Event
import com.mparticle.shared.events.StateStatus

class Exporter(events: List<Event>) {

    var contents: String = ""

    init {
        events.map {
            if (it is StateStatus) {
                it.fields = it.fields.entries.associate {
                    if (it.value is Function0<*>) {
                        it.key to (it.value as Function0<*>).invoke().toString()
                    } else {
                        it.key to it.value
                    }
                }.let { HashMap(it) }
                if (it.obj is InternalSession) {
                    it.fields = (it.obj as InternalSession).getMap().entries.associate {
                        if (it.value is Function0<*>) {
                            it.key to (it.value as Function0<*>).invoke().toString()
                        } else {
                            it.key to it.value
                        }
                    }
                }
            }
        }
        contents = Serializer().serialize(events)

        val deserialized = Serializer().deserialize(contents)
        if (events.size != deserialized.size) {
            throw RuntimeException("serialization failed");
        }
    }

    fun email(emailAddress: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inspector Log: ${Inspector.getInstance()?.application?.packageName}")
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents)
        Inspector.getInstance()?.currentActivity?.get()?.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"))
    }

}
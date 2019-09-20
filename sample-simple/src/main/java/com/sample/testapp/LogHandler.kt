package com.sample.testapp

import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import com.mparticle.MParticle
import com.mparticle.internal.Logger
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class LogHandler: Logger.AbstractLogHandler() {
    val thread = HandlerThread("asdv")
    lateinit var handler: Handler

    init {
        thread.start()
        handler = Handler(thread.looper)
    }

    override fun log(logLevel: MParticle.LogLevel?, throwable: Throwable?, message: String?) {
        val json = JSONObject()
        json.put("key", message)
                .put("log_level", logLevel?.name)
//        json.put("id", UUID.randomUUID().toString())
//        json.put("has_stacktrace", throwable != null)
//        if (throwable != null) {
//            json.put("throwable_message", throwable.message)
//            json.put("throwable_trace", throwable.stackTrace.toString())
//        }

        handler.post {
            val connection = URL("http://52.36.65.97/send")
                    .openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                getOutputStream()
                        .write(json.toString().toByteArray())
            }
        }
    }

}
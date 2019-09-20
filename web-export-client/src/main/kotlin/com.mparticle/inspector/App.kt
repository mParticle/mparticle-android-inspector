package com.mparticle.inspector

import com.mparticle.shared.Serializer
import com.mparticle.shared.ViewControllerManager
import com.mparticle.shared.controllers.CategoryController
import com.mparticle.shared.controllers.StreamController
import kotlinext.js.requireAll
import kotlinx.html.*
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface AppState : RState {
    var recentListController: StreamController
    var replayRecentListController: StreamController
    var shouldDisplayTestRun: Boolean

    var emulators: List<String>
    var versions: List<String>

    var title: String
}

private class App : RComponent<RProps, AppState>() {
    var viewController = ViewControllerManager()
    var replayViewController = ViewControllerManager()

    override fun componentWillMount() {
        setState {
            recentListController = viewController.streamController
            if (viewController.allEvents.size == 0) {
                val file = kotlinext.js.require("test.json")
                val fileString = JSON.stringify(file)
                val events = Serializer().deserialize(fileString)
                console.log("events count: ${events.size}")
                events.forEach { viewController.addEvent(it) }
                console.log("added Events: ${viewController.allEvents.size}")
                console.log("file exists? $file")
                console.log("file length = ${fileString.length}")
            }
            shouldDisplayTestRun = false
            emulators = listOf("Nexus 26", "Samsung 9", "Pixel 3")
            versions = listOf("5.9.1", "5.9.2", "5.9.3","5.9.4","5.9.5","5.9.6","5.9.7", "5.9.8", "5.9.9")
        }
    }

    override fun RBuilder.render() {
        div("content") {
            div("content-left") {
                //                    input(InputType.text) {
////                        attrs.onChangeFunction = {
////                            val target = it.target as HTMLInputElement
////                            viewController.addEvents(target.value)
////                        }
//                    }
                h2 {
                    +"Inspector" // state.title
                }

                recentList(state.recentListController) {
                    setState {
                        this.title = it.name
                    }
                }
            }
            div("content-right") {
                if (state.shouldDisplayTestRun) {

                } else {
                    testRunSelector(state.emulators, state.versions) { emulator, version ->
                        doTestRun(emulator, version)
                    }
                }
            }
        }
    }

    fun doTestRun(emulator: String, version: String) {
        console.log("doing test run on emulator: $emulator, SDK version: $version")
    }
}

fun RBuilder.app() = child(App::class) {
}
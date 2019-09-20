package com.mparticle.inspector

import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.events.Event
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*


interface RunSelectorProps : RProps {
    var emulators: List<String>
    var versions: List<String>
    var block: (String, String) -> Unit
}

class TestRunSelectorComponent: RComponent<RunSelectorProps, RState>() {

    override fun RBuilder.render() {
        form(action = "/run", classes = "version-form") {
            input(classes = "version-select", type = InputType.text) {
                attrs.placeholder = "version"
            }
            input(classes = "version-select", type = InputType.text) {
                attrs.placeholder = "emulator"
            }
            label {
                attrs.htmlFor = "versions"
            }
            select(classes = "version-select") {
                attrs.id = "versions"
                attrs.name = "versions"
                option {
                    +"this"
                }
            }
            button(type = ButtonType.submit) {
                +"Run"
            }
        }
    }

}

fun RBuilder.testRunSelector(emulators: List<String>, versions: List<String>, block: (String, String) -> Unit) =
        child(TestRunSelectorComponent::class) {
            attrs.emulators = emulators
            attrs.versions = versions
            attrs.block = block
        }
package com.mparticle.inspector

import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.events.Event
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

interface LanguageListProps : RProps {
    var data: List<Event>
    var block: (Event) -> Unit
}

class RecentListComponent : RComponent<LanguageListProps, RState>() {
    override fun RBuilder.render() {
        ul("mdc-list mdc-list--two-line") {
            props.data.forEach { item ->
                li("mdc-list-item") {
                    span("mdc-list-item__text") {
                        span("mdc-list-item__primary-text") { +item.name }
                        span("mdc-list-item__secondary-text") { +item.createdTime }
                    }
                    attrs {
                        onClickFunction = {
                            props.block(item)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.recentList(recentListController: StreamController, block: (Event) -> Unit) =
        child(RecentListComponent::class) {
            attrs.data = recentListController.getItems()
            attrs.block = block
        }
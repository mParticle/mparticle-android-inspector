package com.mparticle.inspector

import com.mparticle.shared.controllers.StreamController
import com.mparticle.shared.events.ApiCall
import com.mparticle.shared.events.Event
import com.mparticle.shared.events.MessageEvent
import com.mparticle.shared.events.NetworkRequest
import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

interface LanguageListProps : RProps {
    var data: List<Event>
    var block: (Event) -> Unit
}

class RecentListComponent : RComponent<LanguageListProps, RState>() {
    override fun RBuilder.render() {
        ul("nav-list") {
            console.log("recent list display")
            console.log("data count: ${props.data.size}")
            props.data.forEach { event ->
                event.getSpannable(this)
//
//                li("mdc-list-item") {
//                    span("mdc-list-item__text") {
//                        +"here"
//                        event.getSpannable(this)
////                        span ("mdc-list-item__primary-text") { +event.name }
////                        span("mdc-list-item__secondary-text") { +event.createdTime }
//                    }
//                    attrs {
//                        onClickFunction = {
//                            props.block(event)
//                        }
//
//                    }
//                }
            }
        }
    }



    fun Event.getSpannable(builder: RBuilder): ReactElement {
        val event = this
        return builder.div(classes = "nav-item") {
            shortName(event) {
                div(classes = "item-title-holder") {
                    span(classes = "item-title") {
                        +event.name
                    }
                }
                when (event) {
                    is ApiCall -> {
                    }

                    is NetworkRequest -> {
                        span(classes = "content-subhead") {
                            +(event.status.toString())
                        }
                    }
                    is MessageEvent -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun RDOMBuilder<DIV>.shortName(event: Event, next: (RDOMBuilder<DIV>) -> Unit) {
        div(classes = "short-nav-item-holder") {
            span(classes = "short-nav-item") {
                +"(${event.getShortName()}) "
            }
        }
        next(this)
    }
}

fun RBuilder.recentList(recentListController: StreamController, block: (Event) -> Unit) =
        child(RecentListComponent::class) {
            attrs.data = recentListController.getEvents()
            attrs.block = block
        }
package com.mparticle.inspector

import com.mparticle.shared.ViewControllerManager
import com.mparticle.shared.controllers.CategoryController
import com.mparticle.shared.controllers.StreamController
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface AppState : RState {
    var recentListController: StreamController
    var categoryListController: CategoryController


    var title: String
}

private class App : RComponent<RProps, AppState>() {
    var viewController = ViewControllerManager()

    override fun componentWillMount() {
        setState {
            recentListController = StreamController()
        }
    }

    override fun RBuilder.render() {
        div("mdc-layout-grid") {
            div("mdc-layout-grid__inner") {
                div("mdc-layout-grid__cell mdc-layout-grid__cell") {
                    input(InputType.text) {
                        attrs.onChangeFunction = {
                            val target = it.target as HTMLInputElement
                            viewController.addEvents(target.value)
                        }
                    }
                    h2 {
                        +state.title
                    }
                    recentList(state.recentListController) {
                        setState {
                            this.title = it.name
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {
}
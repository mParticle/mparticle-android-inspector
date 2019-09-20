package com.mparticle.inspector.views

import com.mparticle.shared.events.ApiCall
import com.mparticle.shared.events.Event
import org.w3c.dom.css.CSSStyleSheet
import react.dom.div
import react.dom.h2
import react.*
import styled.styledDiv
import styled.styledH2


sealed class BaseRecentViewHolder(val builder: RBuilder, val shortName: String)


class ApiCallView(props: ApiCallProps): RComponent<ApiCallProps, RState>(props) {
//    val divStyle = {
//        margin
//    }

    override fun RBuilder.render() {
        return

        div {
//            styledDiv {
//
//            }

            h2(classes = "test") {
                "(${props.apiCall.getShortName()})  ${props.apiCall.endpoint}"
            }
        }
    }

}

fun RBuilder.videoList(handler: ApiCallProps.() -> Unit): ReactElement {
    return child(ApiCallView::class) {
        this.attrs(handler)
    }
}

class ApiCallProps(val apiCall: ApiCall): RProps

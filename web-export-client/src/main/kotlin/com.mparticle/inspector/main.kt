package com.mparticle.inspector

import react.dom.render
import kotlin.browser.document

fun main() {
    document.addEventListener("DOMContentLoaded", {
        render(document.getElementById("root")) {
            app()
        }
    })
}
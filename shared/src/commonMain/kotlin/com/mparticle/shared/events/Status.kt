package com.mparticle.shared.events

import kotlinx.serialization.*
import kotlinx.serialization.internal.*

enum class Status {
    Red,
    Green,
    Yellow,
    None
}
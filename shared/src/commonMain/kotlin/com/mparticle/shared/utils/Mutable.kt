package com.mparticle.shared.utils

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class Mutable<T>(var value: T)
package com.github.networkguild.framework

import kotlin.time.Duration

sealed class EventTimeout(val time: Duration) {

    object Inherit : EventTimeout(Duration.ZERO)
}

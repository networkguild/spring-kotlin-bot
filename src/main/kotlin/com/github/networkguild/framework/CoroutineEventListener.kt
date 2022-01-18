package com.github.networkguild.framework

import net.dv8tion.jda.api.events.GenericEvent

fun interface CoroutineEventListener {

    val timeout: EventTimeout get() = EventTimeout.Inherit

    suspend fun onEvent(event: GenericEvent)
}

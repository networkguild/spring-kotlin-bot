package com.github.networkguild.listeners

import com.github.networkguild.framework.CoroutineEventListener
import io.micrometer.core.instrument.Metrics
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageDeleteEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class MessageListener : CoroutineEventListener {

    override suspend fun onEvent(event: GenericEvent) {
        when (event) {
            is MessageReceivedEvent -> Metrics.counter("message.seen").increment()
            is MessageDeleteEvent -> Metrics.counter("message.deleted").increment()
        }
    }
}

package com.github.networkguild.listeners

import io.micrometer.core.instrument.Metrics
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        Metrics.counter("message.seen").increment()
    }
}

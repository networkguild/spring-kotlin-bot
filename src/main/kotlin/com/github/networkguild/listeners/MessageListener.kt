package com.github.networkguild.listeners

import io.micrometer.core.instrument.Metrics
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MessageListener : ListenerAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)
    override fun onMessageReceived(event: MessageReceivedEvent) {
        Metrics.counter("message.seen").increment()
    }
}

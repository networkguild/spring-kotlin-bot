package com.github.networkguild.framework

import kotlinx.coroutines.*
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.IEventManager
import org.slf4j.LoggerFactory
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.time.Duration

@DelicateCoroutinesApi
class CoroutineEventManager(
    scope: CoroutineScope = GlobalScope,
    var timeout: Duration = Duration.INFINITE
) : IEventManager, CoroutineScope by scope {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val listeners = CopyOnWriteArrayList<Any>()

    private fun timeout(listener: Any) = when {
        listener is CoroutineEventListener && listener.timeout != EventTimeout.Inherit -> listener.timeout.time
        else -> timeout
    }

    override fun handle(event: GenericEvent) {
        launch {
            for (listener in listeners) {
                val actualTimeout = timeout(listener)
                if (actualTimeout.isPositive() && actualTimeout.isFinite()) {

                    val result = withTimeoutOrNull(actualTimeout.inWholeMilliseconds) {
                        runListener(listener, event)
                    }
                    if (result == null) {
                        logger.debug("Event of type ${event.javaClass.simpleName} timed out.")
                    }
                } else {
                    runListener(listener, event)
                }
            }
        }
    }

    private suspend fun runListener(listener: Any, event: GenericEvent) {
        when (listener) {
            is CoroutineEventListener -> listener.onEvent(event)
        }
    }

    override fun register(listener: Any) {
        listeners.add(
            when (listener) {
                is CoroutineEventListener -> listener
                else -> throw IllegalArgumentException("Listener must implement using CoroutineEventListener")
            }
        )
    }

    override fun getRegisteredListeners(): MutableList<Any> = mutableListOf(listeners)

    override fun unregister(listener: Any) {
        listeners.remove(listener)
    }
}

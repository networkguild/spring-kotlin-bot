package com.github.networkguild.listeners

import com.github.networkguild.application.UserUseCases
import com.github.networkguild.framework.CoroutineEventListener
import com.github.networkguild.framework.Indexer
import io.micrometer.core.instrument.Metrics
import kotlinx.coroutines.*
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
class InteractionListener(
    private val userUseCases: UserUseCases,
    indexer: Indexer
) : CoroutineEventListener {

    private val supervisor = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val slashCommands = indexer.getCommands()

    override suspend fun onEvent(event: GenericEvent) {
        when (event) {
            is SlashCommandEvent -> {
                Metrics.counter("interaction.used").increment()
                val command = slashCommands[event.name]
                supervisor.launch {
                    command?.handle(event)
                    userUseCases.updateUser(event)
                }
            }
        }
    }
}

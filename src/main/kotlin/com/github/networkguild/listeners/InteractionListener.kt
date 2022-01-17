package com.github.networkguild.listeners

import com.github.networkguild.application.UserUseCases
import com.github.networkguild.framework.Indexer
import io.micrometer.core.instrument.Metrics
import kotlinx.coroutines.*
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class InteractionListener(
    private val userUseCases: UserUseCases,
    indexer: Indexer
) : ListenerAdapter() {

    private val supervisor = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val slashCommandRegistry = indexer.getCommands()

    override fun onSlashCommand(event: SlashCommandEvent) {
        Metrics.counter("interaction.used").increment()
        val command = slashCommandRegistry[event.name]
        supervisor.launch {
            command?.handle(event)
            userUseCases.updateUser(event)
        }
    }
}

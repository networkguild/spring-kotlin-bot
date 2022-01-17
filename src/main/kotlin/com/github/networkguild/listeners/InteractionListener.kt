package com.github.networkguild.listeners

import com.github.networkguild.application.UserUseCases
import com.github.networkguild.domain.PublicSlashCommandRegistry
import io.micrometer.core.instrument.Metrics
import kotlinx.coroutines.*
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class InteractionListener(
    private val publicSlashCommandRegistry: PublicSlashCommandRegistry,
    private val userUseCases: UserUseCases
) : ListenerAdapter() {

    private val supervisor = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onSlashCommand(event: SlashCommandEvent) {
        Metrics.counter("interaction.used").increment()
        val publicCommand = publicSlashCommandRegistry.findCommand(event.name)
        supervisor.launch {
            publicCommand?.handle(event)
            userUseCases.updateUserWithGuild(event)
        }
    }
}

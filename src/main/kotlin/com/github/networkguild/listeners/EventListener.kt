package com.github.networkguild.listeners

import com.github.networkguild.domain.PublicSlashCommandRegistry
import com.github.networkguild.domain.neo4j.Guild
import com.github.networkguild.repository.GuildRepository
import io.micrometer.core.instrument.Metrics
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ReconnectedEvent
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.milliseconds

@Component
class EventListener(
    private val publicSlashCommandRegistry: PublicSlashCommandRegistry,
    private val guildRepository: GuildRepository
) : ListenerAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)
    override fun onReady(event: ReadyEvent) {
        logger.info("Ready with ping ${event.jda.gatewayPing.milliseconds}")
        val listOfCommandData = mutableListOf<CommandData>()
        publicSlashCommandRegistry.forEach { k, v ->
            listOfCommandData.add(CommandData(k, v.properties.description))
        }
        event.jda.updateCommands().addCommands(*listOfCommandData.toTypedArray()).queue()
    }

    override fun onGuildJoin(event: GuildJoinEvent) {
        Metrics.counter("guild.joined").increment()
        val daoGuild = Guild(
            discordId = event.guild.idLong,
            name = event.guild.name,
            memberCount = event.guild.memberCount
        )
        guildRepository.save(daoGuild).block()
    }

    override fun onGuildLeave(event: GuildLeaveEvent) {
        Metrics.counter("guild.leaved").increment()
        guildRepository.deleteById(event.guild.idLong).block()
    }

    override fun onReconnected(event: ReconnectedEvent) {
        Metrics.counter("reconnect").increment()
    }
}

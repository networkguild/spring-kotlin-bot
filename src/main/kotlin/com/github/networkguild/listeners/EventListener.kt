package com.github.networkguild.listeners

import com.github.networkguild.domain.neo4j.Guild
import com.github.networkguild.framework.CoroutineEventListener
import com.github.networkguild.framework.Indexer
import com.github.networkguild.repository.GuildRepository
import io.micrometer.core.instrument.Metrics
import kotlinx.coroutines.reactor.awaitSingle
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ReconnectedEvent
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.milliseconds

@Component
class EventListener(
    private val guildRepository: GuildRepository,
    private val indexer: Indexer
) : CoroutineEventListener {

    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun onEvent(event: GenericEvent) {
        when (event) {
            is ReadyEvent -> {
                logger.info("Ready with ping ${event.jda.gatewayPing.milliseconds}")
                val globalCommandData = indexer.getGlobalCommandData()
                event.jda.updateCommands().addCommands(*globalCommandData.toTypedArray()).queue()
            }
            is GuildJoinEvent -> {
                Metrics.counter("guild.joined").increment()
                val commandData = indexer.getGuildCommandDataWithOptions()
                val daoGuild = Guild(
                    discordId = event.guild.idLong,
                    name = event.guild.name,
                    memberCount = event.guild.memberCount
                )
                guildRepository.save(daoGuild).awaitSingle()
                event.guild.updateCommands().addCommands(*commandData.toTypedArray()).queue()
            }
            is GuildLeaveEvent -> {
                Metrics.counter("guild.leaved").increment()
                guildRepository.deleteById(event.guild.idLong).awaitSingle()
            }
            is ReconnectedEvent -> Metrics.counter("reconnect").increment()
        }
    }
}

package com.github.networkguild.listeners

import com.github.networkguild.domain.discord.DiscordConfigurationProperties
import com.github.networkguild.domain.discord.Embed
import com.github.networkguild.domain.discord.Webhook
import com.github.networkguild.domain.neo4j.Guild
import com.github.networkguild.framework.CoroutineEventListener
import com.github.networkguild.infra.rest.RestClient
import com.github.networkguild.repository.GuildRepository
import com.github.networkguild.toTimeStamp
import com.github.networkguild.utils.Logback
import com.github.networkguild.utils.registerCommands
import com.github.networkguild.utils.registerGlobalCommands
import io.micrometer.core.instrument.Metrics
import kotlinx.coroutines.reactor.awaitSingle
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.ReconnectedEvent
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import org.springframework.stereotype.Component
import java.time.Instant
import kotlin.time.Duration.Companion.milliseconds

@Component
class EventListener(
    private val guildRepository: GuildRepository,
    private val discordConfigurationProperties: DiscordConfigurationProperties,
    private val restClient: RestClient
) : CoroutineEventListener {

    private val logger by Logback<EventListener>()

    override suspend fun onEvent(event: GenericEvent) {
        when (event) {
            is ReadyEvent -> {
                logger.info("Ready with ping ${event.jda.gatewayPing.milliseconds}")
                event.jda.registerGlobalCommands()
                discordConfigurationProperties.webhookError?.let {
                    restClient.sendWebhook(event.jda.getWebHookEmbed(), it)
                }
            }
            is GuildJoinEvent -> {
                val isDevChannel = event.guild.idLong == discordConfigurationProperties.devChannel
                Metrics.counter("guild.joined").increment()
                val daoGuild = Guild(
                    discordId = event.guild.idLong,
                    name = event.guild.name,
                    memberCount = event.guild.memberCount
                )
                guildRepository.save(daoGuild).awaitSingle()
                event.guild.registerCommands(isDevChannel)
            }
            is GuildLeaveEvent -> {
                Metrics.counter("guild.leaved").increment()
                guildRepository.deleteById(event.guild.idLong).awaitSingle()
            }
            is ReconnectedEvent -> Metrics.counter("reconnect").increment()
        }
    }

    private fun JDA.getWebHookEmbed(): Webhook = Webhook(
        username = selfUser.name,
        avatarUrl = selfUser.effectiveAvatarUrl,
        embeds = listOf(
            Embed(
                author = Embed.Author(
                    name = selfUser.name,
                    iconUrl = selfUser.effectiveAvatarUrl
                ),
                title = "${selfUser.name} is ready!",
                url = "https://github.com/networkguild/spring-kotlin-bot",
                description = """
                    **[Spring Boot Grafana dashboard](https://networkguild.grafana.net/goto/9soof91nk?orgId=1)**
                    
                    **[Raspberry Pi Grafana dashboard](https://networkguild.grafana.net/goto/ecF-RC17z?orgId=1)**
                """.trimIndent(),
                color = 0xFF007F,
                footer = Embed.Footer(
                    text = "Provided by ${selfUser.name}",
                    iconUrl = selfUser.effectiveAvatarUrl
                ),
                timestamp = Instant.now().toTimeStamp()
            )
        )
    )
}

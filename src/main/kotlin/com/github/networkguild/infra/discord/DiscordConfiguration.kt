package com.github.networkguild.infra.discord

import com.github.networkguild.domain.discord.DiscordConfigurationProperties
import com.github.networkguild.framework.CoroutineEventManager
import com.github.networkguild.listeners.EventListener
import com.github.networkguild.listeners.InteractionListener
import com.github.networkguild.listeners.MessageListener
import kotlinx.coroutines.DelicateCoroutinesApi
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.Duration.Companion.minutes

@Configuration
class DiscordConfiguration(
    private val discordConfigurationProperties: DiscordConfigurationProperties,
    private val eventListener: EventListener,
    private val interactionListener: InteractionListener,
    private val messageListener: MessageListener
) {
    @OptIn(DelicateCoroutinesApi::class)
    @Bean
    fun jda(): JDA {
        return JDABuilder.createDefault(discordConfigurationProperties.token)
            .addEventListeners(eventListener, interactionListener, messageListener)
            .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .setActivity(Activity.of(Activity.ActivityType.WATCHING, "Kotlin tutorials"))
            .setEventManager(CoroutineEventManager(timeout = 2.minutes))
            .build()
            .awaitReady()
    }
}

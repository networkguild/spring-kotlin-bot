package com.github.networkguild.commands.global.bot

import com.github.networkguild.domain.discord.DiscordConfigurationProperties
import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Change presence of the bot. Owner only", Category.MISC)
class Presence(
    private val discordConfigurationProperties: DiscordConfigurationProperties
) : SlashCommand() {

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(true).queue()
        val status = event.getOption("status")?.asString
        val activity = event.getOption("activity")?.asLong?.toInt()
        val target = requireNotNull(event.getOption("target")?.asString)

        if (event.user.idLong == discordConfigurationProperties.ownerId) {
            val onlineStatus = when (status) {
                "online" -> OnlineStatus.ONLINE
                "idle" -> OnlineStatus.IDLE
                "dnd" -> OnlineStatus.DO_NOT_DISTURB
                "invisible" -> OnlineStatus.INVISIBLE
                else -> OnlineStatus.OFFLINE
            }
            val activityType = when (activity) {
                1 -> Activity.streaming(target, "https://www.twitch.tv/")
                2 -> Activity.listening(target)
                3 -> Activity.competing(target)
                4 -> Activity.playing(target)
                else -> Activity.watching(target)
            }
            event.jda.presence.setPresence(onlineStatus, activityType)
            event.hook.sendMessage("Status changed!").queue()
        } else {
            event.hook.sendMessage("You are not a owner!").queue()
        }
    }
}

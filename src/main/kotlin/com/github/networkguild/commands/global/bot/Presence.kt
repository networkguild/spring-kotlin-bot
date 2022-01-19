package com.github.networkguild.commands.global.bot

import com.github.networkguild.domain.discord.DiscordConfigurationProperties
import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Logback
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Change presence of the bot. Owner only", Category.MISC)
class Presence(
    private val discordConfigurationProperties: DiscordConfigurationProperties
) : SlashCommand() {

    private val logger by Logback<Presence>()
    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(true).queue()
        val options = event.options
        val status = event.getOption("status")?.type

        logger.info("Command option is $status and options $options")

        if (event.user.idLong == discordConfigurationProperties.ownerId) {
            event.jda.presence.setPresence(OnlineStatus.IDLE, Activity.competing("Fishing"))
            event.hook.sendMessage("Presence changed!").queue()
        } else {
            event.hook.sendMessage("You are not a owner!").queue()
        }
    }
}

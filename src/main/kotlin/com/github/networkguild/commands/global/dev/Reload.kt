package com.github.networkguild.commands.global.dev

import com.github.networkguild.domain.discord.DiscordConfigurationProperties
import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.registerCommands
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Reload guild commands. Owner only", Category.OWNER)
class Reload(
    private val discordConfigurationProperties: DiscordConfigurationProperties
) : SlashCommand() {

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(true).queue()
        val isDevChannel = event.guild?.idLong == discordConfigurationProperties.devChannel
        if (event.user.idLong == discordConfigurationProperties.ownerId) {
            event.jda.guilds.map {
                it.registerCommands(isDevChannel)
            }
            event.hook.sendMessage("All guild commands reloaded").setEphemeral(true).queue()
        } else {
            event.hook.sendMessage("You cannot use this command!").setEphemeral(true).queue()
        }
    }
}

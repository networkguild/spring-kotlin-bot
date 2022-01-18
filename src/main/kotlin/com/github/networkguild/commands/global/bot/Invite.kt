package com.github.networkguild.commands.global.bot

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.framework.embed
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Colors
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@CommandProperties(description = "Invite url!", category = Category.MISC)
class Invite : SlashCommand() {

    override suspend fun handle(event: SlashCommandEvent) {
        val bot = event.jda.selfUser
        val inviteUrl = "${event.jda.getInviteUrl(Permission.ADMINISTRATOR)}%20applications.commands"
        event.replyEmbeds(
            embed {
                title = "Yes I'm ready to join"
                url = inviteUrl
                footer {
                    name = "Provided by ${bot.name}"
                    iconUrl = bot.effectiveAvatarUrl
                }
                timestamp = Instant.now()
                color = Colors.randomColor.rgb
            }
        ).setEphemeral(true).queue()
    }
}

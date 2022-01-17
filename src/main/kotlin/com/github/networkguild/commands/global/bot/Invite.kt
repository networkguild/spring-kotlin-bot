package com.github.networkguild.commands.global.bot

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Colors
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@CommandProperties(description = "Invite url!", category = Category.MISC)
class Invite : SlashCommand() {

    override suspend fun handle(event: SlashCommandEvent) {
        val bot = event.jda.selfUser
        val inviteUrl = "${event.jda.getInviteUrl()}%20applications.commands"
        event.replyEmbeds(
            EmbedBuilder()
                .setTitle("Yes I'm ready to join", inviteUrl)
                .setColor(Colors.randomColor.rgb)
                .setFooter("Provided by ${bot.name}", bot.effectiveAvatarUrl)
                .setTimestamp(Instant.now())
                .build()
        ).setEphemeral(true).queue()
    }
}

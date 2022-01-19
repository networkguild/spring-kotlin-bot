package com.github.networkguild.commands.global.dev

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Shows JVM stats and other metrics", category = Category.OWNER)
class Jvm : SlashCommand() {

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply().queue()
        if (event.isFromGuild) {
            event.hook.sendMessage("It is working from guild").queue()
        } else {
            event.hook.sendMessage("It is working from private channel").queue()
        }
    }
}

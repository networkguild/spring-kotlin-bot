package com.github.networkguild.commands.global.bot

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Logback
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Show help", category = Category.MISC)
class Help : SlashCommand() {

    private val logger by Logback<Help>()
    override suspend fun invoke(event: SlashCommandEvent) {
        val commandOption = event.getOption("category")?.asString
        event.reply("Yeah no help command :thinking:").queue()
    }
}

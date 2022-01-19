package com.github.networkguild.commands.guild.dev

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Logback
import com.github.networkguild.utils.registerGlobalCommands
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(
    description = "Register global commands",
    category = Category.DEV,
    guildOnly = true,
    developerOnly = true
)
class Register : SlashCommand() {
    private val logger by Logback<Register>()

    override suspend fun invoke(event: SlashCommandEvent) {
        logger.info("Registering all global commands")
        event.jda.registerGlobalCommands()
        event.reply("Done :white_check_mark:").queue()
    }
}

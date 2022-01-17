package com.github.networkguild.commands.guild.nsfw

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Random picture of butts", category = Category.NSFW, guildOnly = true)
class Butts : SlashCommand() {
    override suspend fun handle(event: SlashCommandEvent) {
        event.reply("Yes no butts yet").queue()

    }
}

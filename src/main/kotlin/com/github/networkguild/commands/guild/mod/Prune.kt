package com.github.networkguild.commands.guild.mod

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
@CommandProperties(description = "Prune messages", Category.MOD, guildOnly = true)
class Prune : SlashCommand() {
    override suspend fun invoke(event: SlashCommandEvent) {
        val count = event.getOption("count")?.asLong ?: 50
        event.deferReply().queue()

        if (event.member?.permissions?.contains(Permission.ADMINISTRATOR) == true) {
            event.channel.iterableHistory.takeAsync(count.toInt()).thenAccept(event.channel::purgeMessages)
            event.hook.sendMessage("$count messages deleted!")
                .delay(10, TimeUnit.SECONDS)
                .flatMap(Message::delete).queue()
        } else {
            event.hook.sendMessage("No permissions to delete messages!")
        }
    }
}

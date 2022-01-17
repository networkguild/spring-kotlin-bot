package com.github.networkguild.commands.global.dev

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.Indexer
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Reload guild commands. Owner only", Category.OWNER)
class Reload(
    private val indexer: Indexer
) : SlashCommand() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun handle(event: SlashCommandEvent) {
        val commands = indexer.getCommands()
        event.deferReply(true).queue()
        if (event.user.idLong == System.getenv("DISCORD_OWNER_ID").toLong()) {
            val guildCommandData = mutableListOf<CommandData>()
            commands.forEach { (k, v) ->
                logger.info("Command name: $k and description: ${v.properties.description}")
                guildCommandData.add(CommandData(k, v.properties.description))
            }
            event.jda.guilds.map {
                it.updateCommands().addCommands(*guildCommandData.toTypedArray())
            }
            event.hook.sendMessage("All guild commands reloaded").setEphemeral(true).queue()
        } else {
            event.hook.sendMessage("You cannot use this command!").setEphemeral(true).queue()
        }
    }
}

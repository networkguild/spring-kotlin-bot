package com.github.networkguild.commands.global.bot

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.Indexer
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.utils.Category
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@CommandProperties(description = "Show help commands", category = Category.MISC)
class Help(indexer: Indexer) : SlashCommand() {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val slashCommandRegistry = indexer.getCommands()

    override val options: OptionData =
        OptionData(OptionType.STRING, "category", "Specify category to get help", false)

    override suspend fun handle(event: SlashCommandEvent) {
        slashCommandRegistry.filterNot { it.value.properties.guildOnly }.map {
            logger.info(it.value.name)
        }
        event.reply("Yeah no help command :thinking:").queue()
    }
}

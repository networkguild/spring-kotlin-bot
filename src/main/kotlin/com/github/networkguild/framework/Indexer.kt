package com.github.networkguild.framework

import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.stereotype.Component

@Component
class Indexer(private val beanFactory: ListableBeanFactory) {

    suspend fun getCommands(): HashMap<String, SlashCommand> {
        val commands = HashMap<String, SlashCommand>()
        beanFactory.getBeansOfType(SlashCommand::class.java).forEach { (key, value) ->
            commands[key] = value
        }
        return commands
    }

    suspend fun getGlobalCommandData(): MutableList<CommandData> {
        val commands = getCommands()
        val globalCommandsData = mutableListOf<CommandData>()
        commands.filterValues { !it.properties.guildOnly }.forEach { (key, value) ->
            globalCommandsData.add(CommandData(key, value.properties.description))
        }
        return globalCommandsData
    }
}

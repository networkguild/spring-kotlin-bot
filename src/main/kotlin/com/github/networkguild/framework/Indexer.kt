package com.github.networkguild.framework

import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.stereotype.Component

@Component
class Indexer(private val beanFactory: ListableBeanFactory) {

    fun getCommands(): HashMap<String, SlashCommand> {
        val commands = HashMap<String, SlashCommand>()
        beanFactory.getBeansOfType(SlashCommand::class.java).forEach {
            commands[it.key] = it.value
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

    fun getGuildCommandDataWithOptions(): MutableList<CommandData> {
        val commands = getCommands()
        val commandDataList = mutableListOf<CommandData>()
        commands.filterValues { it.properties.guildOnly }.forEach { (key, value) ->
            if (value.options != null) {
                val optionData = value.options!!
                commandDataList.add(
                    CommandData(key, value.properties.description)
                        .addOptions(
                            OptionData(
                                optionData.type,
                                optionData.name,
                                optionData.description,
                                optionData.isRequired
                            )
                        )
                )
            } else {
                commandDataList.add(CommandData(key, value.properties.description))
            }
        }
        return commandDataList
    }
}

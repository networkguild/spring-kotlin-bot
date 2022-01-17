package com.github.networkguild.framework

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
}

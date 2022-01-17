package com.github.networkguild.domain

import com.github.networkguild.framework.Indexer
import com.github.networkguild.framework.SlashCommand
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PublicSlashCommandRegistry : HashMap<String, SlashCommand>() {
    private val logger = LoggerFactory.getLogger(javaClass)
    init {
        val indexer = Indexer("com.github.networkguild.commands")
        val commands = indexer.getCommands().associateBy { it.name }
        this.putAll(commands)
        logger.info("Successfully loaded ${commands.size} slash commands!")
    }

    fun findCommand(commandName: String): SlashCommand? = this[commandName]
}

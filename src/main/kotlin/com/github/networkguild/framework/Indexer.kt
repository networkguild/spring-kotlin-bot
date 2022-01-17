package com.github.networkguild.framework

import org.reflections.Reflections
import java.lang.reflect.Modifier

class Indexer(pkg: String) {
    private val reflections = Reflections(pkg)

    fun getCommands(): List<SlashCommand> {
        val allCommands = reflections.getSubTypesOf(SlashCommand::class.java)
            .filter { !Modifier.isAbstract(it.modifiers) && !it.isInterface }
        val slashCommands = mutableListOf<SlashCommand>()
        for (cmd in allCommands) {
            val kls = cmd.getDeclaredConstructor().newInstance()
            slashCommands.add(kls as SlashCommand)
        }
        return slashCommands.toList()
    }
}

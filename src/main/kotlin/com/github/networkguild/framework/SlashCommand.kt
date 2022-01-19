package com.github.networkguild.framework

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class SlashCommand {
    val name: String
        get() = javaClass.simpleName.lowercase()

    val properties: CommandProperties
        get() = javaClass.getAnnotation(CommandProperties::class.java)

    abstract suspend operator fun invoke(event: SlashCommandEvent)
}

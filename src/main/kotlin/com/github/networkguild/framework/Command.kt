package com.github.networkguild.framework

import net.dv8tion.jda.api.interactions.commands.build.OptionData

interface Command<T> {

    val name: String
        get() = javaClass.simpleName.lowercase()

    val properties: CommandProperties
        get() = javaClass.getAnnotation(CommandProperties::class.java)

    val options: List<OptionData>

    suspend fun handle(event: T)
}

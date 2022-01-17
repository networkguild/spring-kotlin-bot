package com.github.networkguild.framework

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.Command.Choice
import net.dv8tion.jda.api.interactions.commands.build.OptionData

abstract class SlashCommand : Command<SlashCommandEvent> {
    override val options: OptionData? = null
    override val choice: List<Choice> = emptyList()
}

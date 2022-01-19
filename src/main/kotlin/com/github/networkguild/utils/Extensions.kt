package com.github.networkguild.utils

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData

fun Guild.registerCommands(isDevGuild: Boolean) {
    updateCommands().run {
        addCommands(
            CommandData("butts", "Random picture of butts")
        )
    }.queue()
    if (isDevGuild) {
        upsertCommand(CommandData("register", "Register global commands")).queue()
    }
}

fun JDA.registerGlobalCommands() {
    updateCommands().run {
        addCommands(
            CommandData("help", "Show help").addOptions(
                OptionData(OptionType.STRING, "category", "Select a category for your help request", true).run {
                    addChoices(
                        Command.Choice("audio", "audio"),
                        Command.Choice("dev", "dev"),
                        Command.Choice("mod", "mod"),
                        Command.Choice("owner", "owner"),
                        Command.Choice("neo4j", "neo4j"),
                        Command.Choice("hacking", "hacking"),
                        Command.Choice("misc", "misc"),
                        Command.Choice("nsfw", "nsfw")
                    )
                }
            ),
            CommandData("presence", "Change presence of the bot. Owner only").addOptions(
                OptionData(OptionType.STRING, "status", "Status of the bot", true).run {
                    addChoices(
                        Command.Choice("online", "online"),
                        Command.Choice("idle", "idle"),
                        Command.Choice("dnd", "dnd"),
                        Command.Choice("invisible", "invisible")
                    )
                },
                OptionData(OptionType.INTEGER, "activity", "Activity type", true).run {
                    addChoices(
                        Command.Choice("streaming", 1),
                        Command.Choice("listening", 2),
                        Command.Choice("competing", 3),
                        Command.Choice("playing", 4),
                        Command.Choice("watching", 5)
                    )
                },
                OptionData(OptionType.STRING, "target", "So what am I doing?", true)
            ),
            CommandData("prune", "Prune messages").addOptions(
                OptionData(OptionType.INTEGER, "count", "The amount of messages to prune. Default 50")
            ),
            CommandData("reload", "Reload guild commands. Owner only"),
            CommandData("invite", "Get invite url!"),
            CommandData("jvm", "Show JVM stats and other metrics")
        )
    }.queue()
}

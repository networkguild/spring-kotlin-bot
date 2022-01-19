package com.github.networkguild.framework

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction

inline fun command(name: String, description: String, builder: CommandData.() -> Unit = {}) =
    CommandData(name, description).apply(builder)

inline fun subcommand(name: String, description: String, builder: SubcommandData.() -> Unit = {}) =
    SubcommandData(name, description).apply(builder)

inline fun <reified T> Option(name: String, description: String, required: Boolean = false, builder: OptionData.() -> Unit = {}): OptionData {
    val type = optionType<T>()
    if (type == OptionType.UNKNOWN)
        throw IllegalArgumentException("Cannot resolve type " + T::class.java.simpleName + " to OptionType!")
    return OptionData(type, name, description).setRequired(required).apply(builder)
}

inline fun <reified T> CommandData.option(name: String, description: String, required: Boolean = false, builder: OptionData.() -> Unit = {}) =
    addOptions(Option<T>(name, description, required, builder))

inline fun <reified T> SubcommandData.option(name: String, description: String, required: Boolean = false, builder: OptionData.() -> Unit = {}) =
    addOptions(Option<T>(name, description, required, builder))

fun CommandListUpdateAction.command(name: String, description: String) = addCommands(command(name, description) {})
fun CommandData.subcommand(name: String, description: String) = addSubcommands(subcommand(name, description) {})

inline fun <reified T> CommandData.option(name: String, description: String, required: Boolean = false) =
    addOptions(Option<T>(name, description, required) {})

inline fun <reified T> SubcommandData.option(name: String, description: String, required: Boolean = false) =
    addOptions(Option<T>(name, description, required) {})

fun OptionData.choice(name: String, value: String) = addChoice(name, value)
fun OptionData.choice(name: String, value: Long) = addChoice(name, value)
fun OptionData.choice(name: String, value: Double) = addChoice(name, value)

inline fun JDA.updateCommands(builder: CommandListUpdateAction.() -> Unit) = updateCommands().apply(builder)
inline fun JDA.upsertCommand(name: String, description: String, builder: CommandData.() -> Unit) =
    upsertCommand(command(name, description, builder))

inline fun Guild.updateCommands(builder: CommandListUpdateAction.() -> Unit) = updateCommands().apply(builder)
inline fun Guild.upsertCommand(name: String, description: String, builder: CommandData.() -> Unit) =
    upsertCommand(command(name, description, builder))

inline fun <reified T> optionType() = when (T::class.java) {
    java.lang.Float::class.java, java.lang.Double::class.java -> OptionType.NUMBER
    Integer::class.java, java.lang.Long::class.java, java.lang.Short::class.java, java.lang.Byte::class.java -> OptionType.INTEGER
    String::class.java -> OptionType.STRING
    User::class.java, Member::class.java -> OptionType.USER
    Role::class.java -> OptionType.ROLE
    java.lang.Boolean::class.java -> OptionType.BOOLEAN
    else -> when {
        Channel::class.java.isAssignableFrom(T::class.java) -> OptionType.CHANNEL
        IMentionable::class.java.isAssignableFrom(T::class.java) -> OptionType.MENTIONABLE
        else -> OptionType.UNKNOWN
    }
}

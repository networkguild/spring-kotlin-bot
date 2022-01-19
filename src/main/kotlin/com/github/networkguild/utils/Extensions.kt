package com.github.networkguild.utils

import com.github.networkguild.framework.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild

fun Guild.registerCommands(isDevGuild: Boolean) {
    updateCommands {
        command("butts", "Random picture of butts")
    }.queue()
    if (isDevGuild) {
        upsertCommand(command("register", "Register global commands")).queue()
    }
}

fun JDA.registerGlobalCommands() {
    updateCommands {
        command(name = "help", description = "Show help") {
            option<Int>(name = "category", description = "Select a category for your help request", required = true) {
                choice(name = "audio", value = 1)
                choice(name = "dev", value = 2)
                choice(name = "mod", value = 3)
                choice(name = "owner", value = 4)
                choice(name = "neo4j", value = 5)
                choice(name = "hacking", value = 6)
                choice(name = "misc", value = 7)
                choice(name = "nsfw", value = 8)
            }
        }
        command("reload", "Reload guild commands per guild. Owner only")
        command(name = "invite", description = "Get invite url!")
        command(name = "jvm", description = "Show JVM stats and other metrics")
        command(name = "presence", description = "Change presence of the bot. Owner only") {
            option<String>(name = "status", description = "Status of the bot") {
                choice(name = "online", value = "online")
                choice(name = "idle", value = "idle")
                choice(name = "dnd", value = "dnd")
                choice(name = "invisible", value = "invisible")
            }
        }
    }.queue()
}

package com.github.networkguild.utils

enum class Category(val title: String, val description: String) {
    AUDIO("\uD83D\uDD08 Audio Commands", "Music related commands."),
    DEV("\u2699\ufe0f Dev Commands", "Debug commands for developers only."),
    HACKING("\u2699\ufe0f Hacking Commands", "Pentesting and hacking related commands."),
    MOD("\uD83D\uDD28 Moderator Commands", "Commands to moderate your server."),
    OWNER(":goat: Owner Commands", "Commands that only bot owner can use."),
    MISC(":information_source: Misc Commands", "Anything not covered by the other categories."),
    NEO4J(":straight_ruler: Neo4j", "Image generation with a memey twist."),
    NSFW(":eyes: NSFW Commands", "Commands to get some nice content.")
}

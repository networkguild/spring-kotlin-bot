package com.github.networkguild.framework

import com.github.networkguild.utils.Category

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CommandProperties(
    val description: String = "No description available",
    val category: Category = Category.MISC,
    val developerOnly: Boolean = false,
    val enabled: Boolean = true,
    val guildOnly: Boolean = false,
    val hidden: Boolean = false,
    val nsfw: Boolean = false
)

package com.github.networkguild.domain.discord

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("discord")
data class DiscordConfigurationProperties(
    val token: String,
    val ownerId: Long,
    val webhookError: String? = null
)

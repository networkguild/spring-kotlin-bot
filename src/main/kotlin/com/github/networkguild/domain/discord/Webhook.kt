package com.github.networkguild.domain.discord

import com.fasterxml.jackson.annotation.JsonProperty

data class Webhook(
    val username: String? = null,
    @field:JsonProperty(value = "avatar_url")
    val avatarUrl: String? = null,
    val content: String? = null,
    val embeds: List<Embed>? = null,
    val attachments: List<Attachment>? = null
)

data class Embed(
    val title: String,
    val url: String? = null,
    val description: String? = null,
    val author: Author? = null,
    val thumbnail: Thumbnail? = null,
    val timestamp: String? = null,
    val image: Image? = null,
    val color: Int? = null,
    val footer: Footer? = null,
    val fields: List<Field>? = null
) {
    data class Thumbnail(val url: String)

    data class Image(val url: String)

    data class Author(
        val name: String,
        val url: String? = null,
        @field:JsonProperty(value = "icon_url")
        val iconUrl: String? = null,
    )

    data class Footer(
        val text: String,
        @field:JsonProperty(value = "icon_url")
        val iconUrl: String? = null
    )

    data class Field(
        val name: String,
        val value: String,
        val inline: Boolean = false
    )
}

data class Attachment(
    val id: Int,
    val description: String,
    val filename: String
)

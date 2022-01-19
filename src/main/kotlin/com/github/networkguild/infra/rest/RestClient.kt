package com.github.networkguild.infra.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.networkguild.JsonConfiguration.Companion.JSON_MAPPER
import com.github.networkguild.domain.NsfwData
import com.github.networkguild.domain.discord.Webhook
import org.springframework.boot.context.properties.bind.Name
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.*

@Component
class RestClient(
    @Name(value = JSON_MAPPER) private val objectMapper: ObjectMapper
) {
    private val httpConnector = ReactorClientHttpConnector()
    private val webClient = WebClient.builder()
        .clientConnector(httpConnector)
        .build()

    suspend fun fetchNsfwByUrl(url: String): List<NsfwData> {
        return webClient.get().uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }

    suspend fun sendWebhook(webhook: Webhook, webHookUri: String) {
        webClient.post().uri(webHookUri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(webhook))
            .retrieve()
            .awaitBodilessEntity()
    }
}

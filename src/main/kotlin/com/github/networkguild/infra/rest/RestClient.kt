package com.github.networkguild.infra.rest

import com.github.networkguild.domain.NsfwData
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class RestClient {

    private val httpConnector = ReactorClientHttpConnector()
    private val webClient = WebClient.builder()
        .clientConnector(httpConnector)
        .build()

    suspend fun fetchNsfwByUrl(url: String): List<NsfwData> {
        return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }
}

package com.github.networkguild.infra.rest

import com.github.networkguild.domain.NsfwData
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class RestClient {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val httpConnector = ReactorClientHttpConnector()
    private val webClient = WebClient.builder()
        .clientConnector(httpConnector)
        .build()

    private suspend fun fetchNsfwByUrl(url: String): NsfwData {
        return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(NsfwData::class.java)
            .awaitSingle()
    }
}

package com.github.networkguild.infra.neo4j

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.neo4j.config.EnableReactiveNeo4jAuditing
import reactor.core.publisher.Mono

@Configuration
@EnableReactiveNeo4jAuditing
class Neo4jConfiguration {
    companion object {
        private const val SOURCE_NAME = "CoroBot"
    }
    @Bean
    fun sourceWriter(): ReactiveAuditorAware<String> = ReactiveAuditorAware<String> { Mono.just(SOURCE_NAME) }
}

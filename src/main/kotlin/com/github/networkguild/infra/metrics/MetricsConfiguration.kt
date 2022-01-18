package com.github.networkguild.infra.metrics

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.MeterBinder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfiguration(
    @Value("\${BUILD_NUMBER:na}") val buildNumber: String,
    @Value("\${COMMIT_SHA:na}") val commitHash: String,
    @Value("\${RELEASE_TAG:na}") val releaseTag: String
) {

    @Bean
    fun metricsCommonTags(): MeterRegistryCustomizer<MeterRegistry> =
        MeterRegistryCustomizer { registry ->
            registry.config().commonTags("app.version", "$releaseTag ($buildNumber / $commitHash)")
        }

    @Bean
    fun processMemoryMetrics(): MeterBinder = ProcessMemoryMetrics()

    @Bean
    fun processThreadMetrics(): MeterBinder = ProcessThreadMetrics()

    @Bean
    fun messageSeenRegistry(registry: MeterRegistry) = registry.counter("message.seen")

    @Bean
    fun interactionRegistry(registry: MeterRegistry) = registry.counter("interaction.used")

    @Bean
    fun guildJoinRegistry(registry: MeterRegistry) = registry.counter("guild.joined")

    @Bean
    fun guildLeaveRegistry(registry: MeterRegistry) = registry.counter("guild.leaved")

    @Bean
    fun reconnectRegistry(registry: MeterRegistry) = registry.counter("reconnect")

    @Bean
    fun messageDeletedRegistry(registry: MeterRegistry) = registry.counter("message.deleted")
}

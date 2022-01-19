package com.github.networkguild

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Configuration
class JsonConfiguration {
    companion object {
        const val JSON_MAPPER = "json_mapper"
    }

    @Bean(name = [JSON_MAPPER])
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().run {
            registerKotlinModule()
            configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        }
    }
}

val timeStampFormat: DateTimeFormatter = DateTimeFormatter
    .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC)

fun Instant?.toTimeStamp() = timeStampFormat.format(this ?: Instant.now()).toString()

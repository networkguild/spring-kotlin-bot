package com.github.networkguild

import org.neo4j.driver.Driver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension

@SpringBootApplication
@EnableReactiveNeo4jRepositories
@ConfigurationPropertiesScan
class Application {

    @Bean(ReactiveNeo4jRepositoryConfigurationExtension.DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
    fun reactiveTransactionManager(driver: Driver, databaseNameProvider: ReactiveDatabaseSelectionProvider) =
        ReactiveNeo4jTransactionManager(driver, databaseNameProvider)
}

fun main(args: Array<String>) {

    fun runApplicationAndFailLoudly(args: Array<String>) {
        try {
            runApplication<Application>(*args)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    runApplicationAndFailLoudly(args)
}

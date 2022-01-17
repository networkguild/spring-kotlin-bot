package com.github.networkguild

import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.data.neo4j.AutoConfigureDataNeo4j
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Neo4jContainer

@DataNeo4jTest
@AutoConfigureDataNeo4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class Neo4jContainerConfig {

    companion object {
        @JvmStatic
        val neo4jContainer: Neo4jContainer<*> = Neo4jContainer("neo4j:4.4.3")

        @JvmStatic
        @DynamicPropertySource
        fun neo4jProperties(registry: DynamicPropertyRegistry) {
            registry.apply {
                add("spring.neo4j.uri", neo4jContainer::getBoltUrl)
                add("spring.neo4j.authentication.username") { "neo4j" }
                add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword)
            }
        }
    }

    init {
        neo4jContainer.start()
    }
}

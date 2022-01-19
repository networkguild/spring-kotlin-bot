package com.github.networkguild.repository

import com.github.networkguild.Neo4jContainerConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional(propagation = Propagation.NEVER)
class GuildRepositoryTest : Neo4jContainerConfig() {

    @Test
    fun findSomethingShouldWork(@Autowired client: Neo4jClient) {
        val result: Optional<Long> = client.query("MATCH (n) RETURN n").fetchAs(Long::class.java).one()
        assertThat(result).isEmpty
    }
}

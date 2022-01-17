package com.github.networkguild.repository

import com.github.networkguild.domain.neo4j.Guild
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository

interface GuildRepository : ReactiveNeo4jRepository<Guild, Long>

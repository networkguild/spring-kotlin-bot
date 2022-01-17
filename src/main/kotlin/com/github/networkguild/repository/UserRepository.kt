package com.github.networkguild.repository

import com.github.networkguild.domain.neo4j.User
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository

interface UserRepository : ReactiveNeo4jRepository<User, Long>

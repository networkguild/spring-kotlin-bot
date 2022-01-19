package com.github.networkguild.domain.neo4j

import org.springframework.data.annotation.*
import org.springframework.data.neo4j.core.schema.DynamicLabels
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.support.DateLong
import java.util.*

abstract class Base(
    @DateLong @CreatedDate @LastModifiedDate var updated: Date? = null,
    @CreatedBy @LastModifiedBy var source: String? = null,
) {
    abstract val discordId: Long
    abstract var version: Long
}

@Node
data class User(
    @Id override val discordId: Long,
    @Version override var version: Long = 0,
    val name: String? = null,
    val commandsUsed: Int = 1,
    @Relationship(type = "BELONGS", direction = Relationship.Direction.OUTGOING)
    val belongsGuild: Set<Guild> = emptySet(),
    @DynamicLabels val labels: Set<String> = emptySet()
) : Base()

@Node
data class Guild(
    @Id override val discordId: Long,
    @Version override var version: Long = 0,
    val name: String? = null,
    val memberCount: Int? = null
) : Base()

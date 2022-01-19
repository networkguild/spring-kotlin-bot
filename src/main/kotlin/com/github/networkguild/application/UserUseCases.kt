package com.github.networkguild.application

import com.github.networkguild.domain.neo4j.User
import com.github.networkguild.repository.GuildRepository
import com.github.networkguild.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserUseCases(
    private val userRepository: UserRepository,
    private val guildRepository: GuildRepository
) {
    suspend fun updateUser(event: SlashCommandEvent) {
        val user = event.user
        val guild = if (event.isFromGuild) {
            val guildId = event.guildChannel.guild.idLong
            setOf(guildRepository.findById(guildId).awaitSingle())
        } else {
            emptySet()
        }
        userRepository.findById(user.idLong).awaitFirstOrNull().run {
            if (this == null) {
                val newUser = User(discordId = user.idLong, name = user.name, belongsGuild = guild)
                userRepository.save(newUser).awaitSingle()
            } else {
                userRepository.save(
                    copy(commandsUsed = commandsUsed + 1, belongsGuild = guild)
                ).awaitSingle()
            }
        }
    }
}

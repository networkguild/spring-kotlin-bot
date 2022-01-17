package com.github.networkguild.application

import com.github.networkguild.domain.neo4j.User
import com.github.networkguild.repository.GuildRepository
import com.github.networkguild.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
class UserUseCases(
    private val userRepository: UserRepository,
    private val guildRepository: GuildRepository
) {
    suspend fun updateUserWithGuild(event: SlashCommandEvent) {
        val user = event.user
        val guildId = event.guildChannel.guild.idLong
        val guild = guildRepository.findById(guildId).awaitSingle()
        userRepository.findById(user.idLong).awaitFirstOrNull().run {
            if (this == null) {
                val newUser = User(discordId = user.idLong, name = user.name, belongsGuild = setOf(guild))
                userRepository.save(newUser).awaitSingle()
            } else {
                userRepository.save(
                    copy(commandsUsed = commandsUsed + 1, belongsGuild = setOf(guild))
                ).awaitSingle()
            }
        }
    }
}

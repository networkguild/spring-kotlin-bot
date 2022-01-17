package com.github.networkguild.commands.guild.nsfw

import com.github.networkguild.framework.CommandProperties
import com.github.networkguild.framework.SlashCommand
import com.github.networkguild.infra.rest.RestClient
import com.github.networkguild.utils.Category
import com.github.networkguild.utils.Colors
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@CommandProperties(description = "Random picture of butts", category = Category.NSFW, guildOnly = true)
class Butts(private val restClient: RestClient) : SlashCommand() {

    private companion object {
        const val BUTT_RANDOM_URL = "http://api.obutts.ru/butts/0/1/random"
        const val BUTT_IMAGE_PREFIX_URL = "http://media.obutts.ru/"
    }

    override suspend fun handle(event: SlashCommandEvent) {
        event.deferReply().queue()
        if (event.textChannel.isNSFW) {
            val nsfwData = restClient.fetchNsfwByUrl(BUTT_RANDOM_URL)
            event.hook.sendMessageEmbeds(
                EmbedBuilder()
                    .setImage("$BUTT_IMAGE_PREFIX_URL${nsfwData.first().preview}")
                    .setColor(Colors.randomColor.rgb)
                    .setFooter("Butts by ${event.jda.selfUser.name}", event.jda.selfUser.effectiveAvatarUrl)
                    .setTimestamp(Instant.now())
                    .build()
            ).queue()
        } else {
            event.hook.sendMessage("What are you doing!? Not a NSFW channel!").queue()
        }
    }
}

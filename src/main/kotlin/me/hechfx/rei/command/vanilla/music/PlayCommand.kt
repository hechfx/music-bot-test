package me.hechfx.rei.command.vanilla.music

import dev.schlaubi.lavakord.rest.TrackResponse
import dev.schlaubi.lavakord.rest.loadItem
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface
import me.hechfx.rei.lavalink.PlayerManager
import java.net.URL

class PlayCommand : CommandInterface {
    override val labels = listOf("play", "p", "tocar")
    override val description = "plays music"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players.getOrPut(context.guild.id.value) { PlayerManager(context.chokita.lavakord.getLink(context.guild.id.value)) }
        val query = context.args.joinToString(" ")
        val voiceState = context.author.asMember(context.guild.id).getVoiceState()
        val channelId = voiceState.channelId

        if (query.isEmpty()) {
            context.reply(
                "You need to provide a link/name for the song."
            )
            return
        }

        val search = if (isValidUrl(query)) {
            query
        } else {
            "ytsearch: $query"
        }

        if (channelId == null) {
            context.reply(
                "You aren't connected in a voice channel."
            )
            return
        }

        instance.link.connectAudio(channelId.value)

        val item = instance.link.loadItem(search)

        when (item.loadType) {
            TrackResponse.LoadType.SEARCH_RESULT -> {
                instance.scheduler.queue(item.tracks.first())

                context.reply(
                    "Adding `${item.tracks.first().info.title}` by `${item.tracks.first().info.author}` to the queue."
                )
            }

            TrackResponse.LoadType.TRACK_LOADED -> {
                instance.scheduler.queue(item.tracks.first())

                context.reply(
                    "Adding `${item.tracks.first().info.title}` by `${item.tracks.first().info.author}` to the queue."
                )
            }

            TrackResponse.LoadType.PLAYLIST_LOADED -> {
                item.tracks.take(30).forEach {
                    instance.scheduler.queue(it)
                }

                context.reply(
                    "Adding `${item.tracks.take(15).size}` songs to the queue."
                )
            }

            else -> {}
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }
}
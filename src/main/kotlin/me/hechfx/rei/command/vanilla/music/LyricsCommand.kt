package me.hechfx.rei.command.vanilla.music

import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface
import me.hechfx.rei.response.LyricsResponse
import me.hechfx.rei.util.GeneralUtils.http

class LyricsCommand : CommandInterface {

    override val labels = listOf("lyrics", "letra")
    override val description = "see the lyrics of a music"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        if (context.args.isEmpty()) {
            val instance = Chokita.players[context.guild.id.value]

            if (instance == null || instance.player.playingTrack == null) {
                context.reply(
                    "Usage: `${context.chokita.generalConfig.discordConfig.prefix}lyrics [song title]`"
                )
                return
            }

            val req: String = http.get("https://some-random-api.ml/lyrics") {
                parameter("title", instance.player.playingTrack!!.title)
            }

            val transform: LyricsResponse = Json.decodeFromString(req)

            context.createEmbed {
                title = "${transform.title} - ${transform.author}"
                description = transform.lyrics
            }
        } else {
            val songTitle = context.args.joinToString(" ")

            val req: String = http.get("https://some-random-api.ml/lyrics") {
                parameter("title", songTitle)
            }

            val transform: LyricsResponse = Json.decodeFromString(req)

            context.createEmbed {
                title = "${transform.title} - ${transform.author}"
                description = transform.lyrics
            }
        }
    }
}
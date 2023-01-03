package me.hechfx.rei.command.vanilla.music

import dev.schlaubi.lavakord.audio.player.Track
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class QueueCommand : CommandInterface {
    override val labels = listOf("queue", "playlist", "musicas", "lista")
    override val description = "See the music playlist"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]

        if (instance == null) {
            context.reply(
                "There aren't any song playing right now."
            )
            return
        }

        val queue = instance.scheduler.queue

        var string = ""

        for ((i, e) in queue.withIndex()) {
            string += "**${i+1}** | `${e.info.title}` by `${e.info.author}` - `${e.toTrack().length}`\n"
        }

        context.createEmbed {
            title = "Current Playlist - ${queue.size} ${if (queue.size == 1) "Song" else "Songs"}"

            description = "> Playing now: `${instance.player.playingTrack?.title}` by `${instance.player.playingTrack?.author}`\n\n$string"
        }
    }
}
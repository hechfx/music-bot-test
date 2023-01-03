package me.hechfx.rei.command.vanilla.music

import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class NowPlayingCommand : CommandInterface {
    override val labels = listOf("np", "nowplaying")
    override val description = "see the current playing song"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]

        if (instance == null || instance.player.playingTrack == null) {
            context.reply(
                "There aren't any song playing right now."
            )
            return
        }

        context.reply(
            "Playing now: `${instance.player.playingTrack!!.title}` by `${instance.player.playingTrack!!.author}` | Length: `${instance.player.playingTrack!!.length}`"
        )
    }
}
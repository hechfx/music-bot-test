package me.hechfx.rei.command.vanilla.music

import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class StopCommand : CommandInterface {
    override val labels = listOf("stop", "parar", "leave", "sair")
    override val description = "stops the music"
    override val devMode = true

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]
        val channelId = context.author.asMember(context.guild.id).getVoiceState().channelId

        if (instance == null) {
            context.reply("There is no song playing right now.")
            return
        }

        if (instance.link.lastChannelId != channelId?.value) {
            context.reply("You aren't connected in the same voice channel as me.")
            return
        }

        instance.link.player.stopTrack()
        instance.link.disconnectAudio()
        instance.scheduler.queue.clear()
        Chokita.players.remove(context.guild.id.value)

        context.reply(
            "Stopping the music, cleaning the queue and leaving the voice channel! Bye."
        )
    }
}
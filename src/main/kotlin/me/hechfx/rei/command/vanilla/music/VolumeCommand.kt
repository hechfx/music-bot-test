package me.hechfx.rei.command.vanilla.music

import dev.schlaubi.lavakord.audio.player.applyFilters
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class VolumeCommand : CommandInterface {
    override val labels = listOf("volume", "vol")
    override val description = "change the volume and equalizers too"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]

        if (instance == null) {
            context.reply(
                "There aren't any song playing right now."
            )
            return
        }
        val channelId = context.author.asMember(context.guild.id).getVoiceState().channelId

        if (channelId?.value != instance.link.lastChannelId) {
            context.reply(
                "You aren't connected at the same voice channel as me."
            )
            return
        }

        if (context.args.isEmpty()) {
            context.reply(
                "The volume is `${instance.player.volume}`."
            )
            return
        }

        if (context.args[0].toIntOrNull() != null) {
            val volume = context.args[0].toFloat()

            if (volume <= 150.0F || volume >= 50.0F) {
                instance.player.applyFilters {
                    this.volume = volume
                }
            }

            context.reply(
                "Now the volume is `${volume}`."
            )
        }
    }
}
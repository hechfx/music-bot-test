package me.hechfx.rei.command.vanilla.music

import dev.kord.core.entity.ReactionEmoji
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class SkipCommand : CommandInterface {
    override val labels = listOf("skip", "pular", "s")
    override val description = "skips the music"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]
        val voiceState = context.author.asMember(context.guild.id).getVoiceState()

        if (instance == null || instance.player.playingTrack == null) {
            context.reply(
                "There aren't any song playing right now."
            )
            return
        }

        if (voiceState.channelId?.value != instance.link.lastChannelId) {
            context.reply(
                "You aren't in the same voice channel as me."
            )
            return
        }

        if (instance.scheduler.queue.size == 0)  {
            context.reply(
                "This is the last music of the queue, use `${context.chokita.generalConfig.discordConfig.prefix}stop` instead."
            )
            return
        } else {
            instance.scheduler.nextTrack()
            context.addReaction("✅")
        }
    }
}
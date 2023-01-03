package me.hechfx.rei.command.vanilla.music

import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class RemoveCommand : CommandInterface {
    override val labels = listOf("remove", "remover")
    override val description = "remove a song from the queue"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]
        val channelId = context.author.asMember(context.guild.id).getVoiceState().channelId

        if (instance == null) {
            context.reply(
                "There aren't any song playing right now."
            )
            return
        }

        if (instance.link.lastChannelId != channelId?.value) {
            context.reply("You aren't connected in the same voice channel as me.")
            return
        }

        val queue = instance.scheduler.queue

        if (context.args.isEmpty()) {
            context.reply(
                "You need to provide a number between 1 and ${queue.size}"
            )
            return
        }

        if (context.args.getOrNull(0) != null && context.args.getOrNull(0) == "all") {
            queue.clear()
            context.reply(
                "Removed all songs."
            )
            return
        }

        val input = try {
            context.args[0].toInt()
        } catch (e: Exception) {
            context.reply(
                "Invalid input, you need to insert a number between 1 and ${queue.size}"
            )
            return
        }

        try {
            val removed = queue.removeAt(input-1)
            context.reply(
                "Removed `${removed.info.title}` by `${removed.info.author}`."
            )
        } catch (e: Exception) {
            context.reply(
                "Invalid input, you need to insert a number between 1 and ${queue.size}."
            )
            return
        }
    }
}
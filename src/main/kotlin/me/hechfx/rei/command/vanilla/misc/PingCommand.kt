package me.hechfx.rei.command.vanilla.misc

import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class PingCommand: CommandInterface {
    override val labels = listOf("ping")
    override val description = "test command owo"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val ms = System.currentTimeMillis()
        val message = context.reply(
            "Pong! BOT: `${context.chokita.client.gateway.averagePing}`/ API: `...`"
        )

        val diff = System.currentTimeMillis() - ms
        context.editReply(
            message,
            "Pong! BOT: `${context.chokita.client.gateway.averagePing}` / API: `${diff}ms`"
        )
    }
}
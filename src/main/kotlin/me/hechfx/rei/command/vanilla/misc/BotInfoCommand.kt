package me.hechfx.rei.command.vanilla.misc

import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

class BotInfoCommand : CommandInterface {
    override val labels = listOf("botinfo")
    override val description = "see the bot info"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val jvm = ManagementFactory.getRuntimeMXBean()
        val uptime = jvm.uptime
        val days = TimeUnit.MILLISECONDS.toDays(uptime)
        val hours = TimeUnit.MILLISECONDS.toHours(uptime) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(uptime) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(uptime) % 60

        context.createEmbed {
            title = "Bot information"
            description = "```Uptime: ${days}d, ${hours}h, ${minutes}m, ${seconds}s```"
            field {
                name = "JVM Version"
                value = "```${jvm.vmVersion}```"
                inline = true
            }
            field {
                name = "Players"
                value = "```${Chokita.players.size}```"
                inline = true
            }
        }
    }
}
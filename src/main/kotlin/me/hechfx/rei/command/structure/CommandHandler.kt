package me.hechfx.rei.command.structure

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Role
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import me.hechfx.rei.Chokita
import mu.toKLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommandHandler {
    companion object {
        val logger = LoggerFactory.getLogger(CommandHandler::class.java).toKLogger()
    }

    fun startHandling(chokita: Chokita) = chokita.client.on<MessageCreateEvent> {
        if (
            !message.content.startsWith(chokita.generalConfig.discordConfig.prefix)
            || message.getGuildOrNull() == null
            || message.author?.isBot == true
        ) {
            return@on
        }

        val args = message.content.substring(chokita.generalConfig.discordConfig.prefix.length, message.content.length)
            .trim()
            .split(" ")

        val input = args[0]
        val command = chokita.commandManager[input]
        val context = CommandContext(
            message,
            args.drop(1),
            message.author!!,
            message.getGuild(),
            chokita
        )

        if (
            chokita.generalConfig.discordConfig.availableChannels.contains(context.channel.id.value.toLong())
            || context.author.asMember(context.guild.id).roleIds.contains(Snowflake("351473717194522647"))
            || context.author.asMember(context.guild.id).roleIds.contains(Snowflake("505144985591480333"))
        ) {
            command?.execute(context).also {
                logger.info {
                    "${context.author.tag} (${context.author.id.value}) used $input in ${context.guild.name} (${context.guild.id.value})"
                }
            }
        }
    }
}
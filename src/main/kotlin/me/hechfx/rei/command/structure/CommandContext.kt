package me.hechfx.rei.command.structure

import dev.kord.common.entity.DiscordEmoji
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.reply
import dev.kord.core.cache.data.EmojiData
import dev.kord.core.entity.*
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.MessageCreateBuilder
import me.hechfx.rei.Chokita

open class CommandContext(
    val message: Message,
    val args: List<String>,
    val author: User,
    val guild: Guild,
    val chokita: Chokita,
    val channel: MessageChannelBehavior = message.channel
) {
    suspend fun addReaction(id: String, unicode: Boolean? = true, name: String? = null) {
        if (unicode == true) {
            message.addReaction(ReactionEmoji.Unicode(id))
        } else {
            message.addReaction(
                GuildEmoji(
                    EmojiData.from(
                        guild.id,
                        Snowflake(id),
                        DiscordEmoji(
                            Snowflake(id),
                            name
                        )
                    ),
                    chokita.client
                )
            )
        }
    }

    suspend fun sendMessage(builder: MessageCreateBuilder.() -> Unit) = channel.createMessage(builder)

    suspend fun reply(input: String, prefix: String? = null): Message {
        return if (prefix == null) {
            message.reply {
                content = "\uD83C\uDFD3 **|** $input"
            }
        } else {
            message.reply {
                content = "$prefix **|** $input"
            }
        }
    }

    open suspend fun editReply(message: Message, input: String, prefix: String? = null): Message {
        return if (prefix == null) {
            message.edit {
                content = "\uD83C\uDFD3 **|** $input"
            }
        } else {
            message.edit {
                content = "$prefix **|** $input"
            }
        }
    }

    suspend fun createEmbed(builder: EmbedBuilder.() -> Unit) = channel.createEmbed(builder)
}
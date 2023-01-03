package me.hechfx.rei

import dev.kord.core.Kord
import dev.kord.core.enableEvent
import dev.kord.core.entity.Guild
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.user.PresenceUpdateEvent
import dev.kord.core.event.user.VoiceStateUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intents
import dev.schlaubi.lavakord.LavaKord
import dev.schlaubi.lavakord.kord.lavakord
import me.hechfx.rei.command.structure.CommandHandler
import me.hechfx.rei.command.structure.CommandManager
import me.hechfx.rei.command.structure.CommandService
import me.hechfx.rei.config.GeneralConfig
import me.hechfx.rei.lavalink.PlayerManager
import mu.toKLogger
import org.slf4j.LoggerFactory

class Chokita(val generalConfig: GeneralConfig) {
    lateinit var client: Kord
    lateinit var lavakord: LavaKord
    val commandManager = CommandManager()
    val commandHandler = CommandHandler()

    companion object {
        val players = mutableMapOf<ULong, PlayerManager>()
    }

    val logger = LoggerFactory.getLogger(Chokita::class.java).toKLogger()

    suspend fun start() {
        client = Kord(generalConfig.discordConfig.token)
        lavakord = client.lavakord()
        lavakord.addNode(
            "ws://${generalConfig.lavalinkConfig.host}:${generalConfig.lavalinkConfig.port}",
            generalConfig.lavalinkConfig.password
        )

        CommandService(commandManager).registerCommands()
        commandHandler.startHandling(this)

        client.on<ReadyEvent> {
            logger.info { "Logged in to Discord's WebSocket!" }
        }

        client.login {
            presence {
                listening("use ;play ^^")
            }
            intents = Intents {
                enableEvent<MessageCreateEvent>()
                enableEvent<PresenceUpdateEvent>()
                enableEvent<VoiceStateUpdateEvent>()
            }
        }
    }
}
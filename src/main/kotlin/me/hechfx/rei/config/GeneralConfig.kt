package me.hechfx.rei.config

import kotlinx.serialization.Serializable

@Serializable
data class GeneralConfig(
    val lavalinkConfig: LavalinkConfig,
    val discordConfig: DiscordConfig
) {
    @Serializable
    data class LavalinkConfig(
        val host: String,
        val port: Int,
        val password: String
    )

    @Serializable
    data class DiscordConfig(
        val token: String,
        val prefix: String,
        val availableChannels: List<Long>
    )
}

package me.hechfx.rei.command.vanilla.music

import dev.schlaubi.lavakord.audio.player.Band
import dev.schlaubi.lavakord.audio.player.applyFilters
import me.hechfx.rei.Chokita
import me.hechfx.rei.command.structure.CommandContext
import me.hechfx.rei.command.structure.CommandInterface

class BassBoostCommand : CommandInterface {
    override val labels = listOf("bassboost")
    override val description = "increase the bass"
    override val devMode = false

    override suspend fun execute(context: CommandContext) {
        val instance = Chokita.players[context.guild.id.value]

        if (instance == null) {
            context.reply(
                "There is no music playing right now."
            )
            return
        }

        if (instance.player.playingTrack != null) {
            val level = context.args.getOrNull(0)

            if (level == null) {
                context.reply(
                    "Bass Bosst levels: `low`, `medium`, `high`, `extreme`"
                )
                return
            }

            instance.player.applyFilters {
                when (level) {
                    "low" -> if (bands.isEmpty()) bands.add(Band(1, 0.25F))
                    "medium" -> if (bands.isEmpty()) bands.add(Band(1, 0.50F))
                    "high" -> if (bands.isEmpty()) bands.add(Band(1, 0.75F))
                    "extreme" -> if (bands.isEmpty()) bands.add(Band(1, 1.00F))
                    "clear" -> if (bands.isNotEmpty()) bands.clear()
                }
            }

            context.addReaction("✅")
        }
    }
}
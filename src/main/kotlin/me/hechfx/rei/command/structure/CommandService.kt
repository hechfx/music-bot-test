package me.hechfx.rei.command.structure

import me.hechfx.rei.command.vanilla.misc.*
import me.hechfx.rei.command.vanilla.music.*

class CommandService(val commandManager: CommandManager) {
    fun registerCommands() {
        commandManager.addCommands(
            // ==[ Miscellaneous ]==
            PingCommand(),
            BotInfoCommand(),

            // ==[ Music ]==
            PlayCommand(),
            StopCommand(),
            QueueCommand(),
            NowPlayingCommand(),
            RemoveCommand(),
            SkipCommand(),
            VolumeCommand(),
            LyricsCommand(),
            BassBoostCommand()
        )
    }
}
package me.hechfx.rei.command.structure

class CommandManager {
    val commands = mutableSetOf<CommandInterface>()

    operator fun get(commandName: String): CommandInterface? {
        return commands.firstOrNull { it.labels.contains(commandName) }
    }

    fun addCommands(vararg command: CommandInterface) {
        commands.addAll(command)
    }
}
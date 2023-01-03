package me.hechfx.rei.command.structure

interface CommandInterface {
    val labels: List<String>
    val description: String
    val devMode: Boolean

    suspend fun execute(context: CommandContext)
}
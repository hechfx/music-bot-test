package me.hechfx.rei.boot

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.hocon.Hocon
import me.hechfx.rei.Chokita
import me.hechfx.rei.config.GeneralConfig
import me.hechfx.rei.util.GeneralUtils.decodeFromFile
import java.io.File

object BootService {
    @JvmStatic
    fun main(args: Array<String>) {
        val configFile = File("./general.conf")

        if (!configFile.exists()) {
            val content = """
                lavalinkConfig {
                    host = "localhost"
                    port = 5432
                    password = "youshallnotpass"
                }
                
                discordConfig {
                    token = "insert your token here!"
                    prefix = ";"
                    availableChannels = [
                        123,
                        456,
                        789
                    ]
                }
            """.trimIndent()

            configFile.writeBytes(content.toByteArray())
        } else {
            val generalConfig: GeneralConfig = Hocon.decodeFromFile(configFile)

            runBlocking {
                Chokita(generalConfig).start()
            }
        }
    }
}
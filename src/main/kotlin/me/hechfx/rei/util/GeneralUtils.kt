package me.hechfx.rei.util

import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.slf4j.Logger
import java.io.File

object GeneralUtils {
    inline fun <reified T> Hocon.decodeFromFile(file: File): T = decodeFromConfig(ConfigFactory.parseFile(file).resolve())

    val http = HttpClient()
}
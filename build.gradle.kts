import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "me.hechfx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://schlaubi.jfrog.io/artifactory/lavakord")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.36")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("dev.kord:kord-core:0.8.0-M12")
    implementation("dev.schlaubi.lavakord:kord:3.5.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    shadowJar {
        manifest {
            attributes["Main-Class"] = "me.hechfx.rei.boot.BootService"
        }
    }
}

application {
    mainClass.set("me.hechfx.rei.boot.BootService")
}
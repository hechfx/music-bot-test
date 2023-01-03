package me.hechfx.rei.lavalink

import dev.schlaubi.lavakord.audio.Link

class PlayerManager(val link: Link) {
    val scheduler = TrackScheduler(link)
    val player = link.player
}
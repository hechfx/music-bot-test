package me.hechfx.rei.lavalink

import dev.schlaubi.lavakord.audio.Link
import dev.schlaubi.lavakord.audio.TrackEndEvent
import dev.schlaubi.lavakord.audio.TrackEvent
import dev.schlaubi.lavakord.audio.on
import dev.schlaubi.lavakord.rest.TrackResponse
import java.util.*

class TrackScheduler(private val link: Link) {
    val queue = LinkedList<TrackResponse.PartialTrack>()

    suspend fun queue(track: TrackResponse.PartialTrack) {
        if (link.player.playingTrack != null) {
            queue.offer(track)
        } else {
            link.player.playTrack(track)
        }
    }

    suspend fun nextTrack() {
        link.player.playTrack(queue.poll())
    }

    init {
        link.player.on<TrackEvent, TrackEndEvent> {
            when (reason) {
                TrackEndEvent.EndReason.FINISHED -> {
                    if (queue.isNotEmpty()) {
                        nextTrack()
                    }
                }
                else -> {}
            }
        }
    }
}
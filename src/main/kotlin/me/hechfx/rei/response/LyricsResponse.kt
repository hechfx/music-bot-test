package me.hechfx.rei.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LyricsResponse(
    val title: String,
    val author: String,
    val lyrics: String,
    val thumbnail: LyricsThumbnailResponse,
    val links: LyricsLinksResponse,
    val disclaimer: String
) {
    @Serializable
    data class LyricsThumbnailResponse(
        @SerialName("genius")
        val url: String
    )

    @Serializable
    data class LyricsLinksResponse(
        @SerialName("genius")
        val url: String
    )
}

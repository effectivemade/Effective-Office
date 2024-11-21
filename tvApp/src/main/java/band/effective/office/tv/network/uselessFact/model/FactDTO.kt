package band.effective.office.tv.network.uselessFact.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FactDTO(
    val id: String,
    val language: String,
    val permalink: String,
    val source: String,
    val source_url: String,
    val text: String
)
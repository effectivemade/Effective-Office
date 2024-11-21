package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Course(
    val authorId: String,
    val crowns: Int,
    val fromLanguage: String,
    val healthEnabled: Boolean,
    val id: String,
    val learningLanguage: String,
    val placementTestAvailable: Boolean,
    val preload: Boolean,
    val title: String,
    val xp: Int
)
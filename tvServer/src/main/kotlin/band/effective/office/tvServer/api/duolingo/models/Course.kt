package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable

@Serializable
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
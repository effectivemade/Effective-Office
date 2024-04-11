package band.effective.office.tvServer.model

import band.effective.office.tvServer.api.duolingo.models.User
import kotlinx.serialization.Serializable

@Serializable
data class DuolingoUser(
    val username: String,
    val totalXp: Int,
    val photo: String,
    val streakDay: Int,
    val countryLang: List<String>
)

fun User.toDomain(): DuolingoUser {
    return DuolingoUser(
        username = name ?: username,
        totalXp = totalXp,
        photo = parseUrlForDuolingoAvatar(picture),
        streakDay = streak,
        countryLang = courses.map { it.learningLanguage }
    )
}

fun parseUrlForDuolingoAvatar(url: String) = "https:$url/xxlarge"
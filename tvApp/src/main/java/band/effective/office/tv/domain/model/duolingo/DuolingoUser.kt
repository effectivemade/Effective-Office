package band.effective.office.tv.domain.model.duolingo

import band.effective.office.tv.domain.model.DomainModel
import band.effective.office.tv.network.duolingo.models.DuolingoResponse

data class DuolingoUser(
    val username: String,
    val totalXp: Int,
    val photo: String,
    val streakDay: Int,
    val countryLang: List<String>
): DomainModel()

fun DuolingoResponse.toDomain(): DuolingoUser? {
    return if (users.isEmpty()) null
    else {
        val user = users.first()
        DuolingoUser(
            username = user.name ?: user.username,
            totalXp = user.totalXp,
            photo = parseUrlForDuolingoAvatar(user.picture),
            streakDay = user.streak,
            countryLang = user.courses.map { it.learningLanguage }
        )
    }
}
fun parseUrlForDuolingoAvatar(url: String) = "https:$url/xxlarge"
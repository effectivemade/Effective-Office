package band.effective.office.tv.screen.duolingo.model

import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.screen.duolingo.utils.mapLanguagesToFlag

data class DuolingoUserUI(
    val username: String,
    val totalXp: Int,
    val photo: String,
    val streakDay: Int,
    val countryLang: List<FlagInfo>
)

fun DuolingoUser.toUI(): DuolingoUserUI {
    return DuolingoUserUI(
        username = username,
        totalXp = totalXp,
        photo = photo,
        streakDay = streakDay,
        countryLang = countryLang.map { FlagInfo(mapLanguagesToFlag(it)) }
    )
}
fun List<DuolingoUser>.toUI() = map { it.toUI() }

data class FlagInfo(val drawableId: Int)
package band.effective.office.tvServer.api.duolingo

import band.effective.office.tvServer.api.duolingo.models.DuolingoResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DuolingoApiImpl(private val client: HttpClient) : DuolingoApi {
    private val baseUrl = "https://www.duolingo.com/2017-06-30"
    override suspend fun getUser(username: String): DuolingoResponse =
        client.get(
            "$baseUrl/users?fields=users{courses,creationDate,currentCourse," +
                    "currentCourseId,globalAmbassadorStatus,hasPlus,id," +
                    "lingots,name,picture,privacySettings,roles,streak," +
                    "streakData{currentStreak,previousStreak},totalXp,username"
        ) {
            parameter("username", username)
        }.body()
}
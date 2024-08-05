package band.effective.office.tv.network.duolingo

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.duolingo.models.DuolingoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DuolingoApi {
    @GET("users?fields=users{courses,creationDate,currentCourse," +
            "currentCourseId,globalAmbassadorStatus,hasPlus,id," +
            "lingots,name,picture,privacySettings,roles,streak," +
            "streakData{currentStreak,previousStreak},totalXp,username")
    @Headers(
        "User-Agent: Mozilla/5.0 (Android 13; Mobile; rv:68.0) Gecko/68.0 Firefox/112.0",
        "Accept: */*"
    )
    suspend fun getUserInfo(@Query("username") username: String): Either<ErrorReason, DuolingoResponse>
}
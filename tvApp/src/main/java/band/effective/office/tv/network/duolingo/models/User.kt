package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val _achievements: List<Any>?,
    val achievements: List<Any>?,
    val acquisitionSurveyReason: String,
    val betaStatus: String,
    val bio: String,
    val canUseModerationTools: Boolean,
    val classroomLeaderboardsEnabled: Boolean,
    val courses: List<Course>,
    val creationDate: Int,
    val currentCourseId: String,
    val emailVerified: Boolean,
    val fromLanguage: String,
    val globalAmbassadorStatus: GlobalAmbassadorStatus,
    val hasFacebookId: Boolean,
    val hasGoogleId: Boolean,
    val hasPhoneNumber: Boolean,
    val hasPlus: Boolean,
    val hasRecentActivity15: Boolean,
    val id: Int,
    val joinedClassroomIds: List<Any>?,
    val learningLanguage: String,
    val liveOpsFeatures: List<LiveOpsFeature>?,
    val motivation: String,
    val name: String?,
    val observedClassroomIds: List<Any>?,
    val picture: String,
    val privacySettings: List<Any>?,
    val profileCountry: String?,
    val roles: List<String>,
    val shakeToReportEnabled: Any?,
    val shouldForceConnectPhoneNumber: Boolean,
    val streak: Int,
    val streakData: StreakData,
    val totalXp: Int,
    val username: String
)
package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
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
    val hasFacebookId: Boolean,
    val hasGoogleId: Boolean,
    val hasPhoneNumber: Boolean,
    val hasPlus: Boolean,
    val hasRecentActivity15: Boolean,
    val id: Int,
    val learningLanguage: String,
    val liveOpsFeatures: List<LiveOpsFeature>?,
    val motivation: String,
    val name: String?,
    val picture: String,
    val profileCountry: String?,
    val roles: List<String>,
    val shouldForceConnectPhoneNumber: Boolean,
    val streak: Int,
    val streakData: StreakData,
    val totalXp: Int,
    val username: String
)
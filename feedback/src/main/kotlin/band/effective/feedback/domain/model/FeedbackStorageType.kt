package band.effective.feedback.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class FeedbackStorageType {
    YDB, MATTERMOST, NOTION, UNSUPPORTED
}
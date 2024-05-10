package band.effective.feedback.domain.model

sealed class FeedbackStorage(val type: FeedbackStorageType) {
    data object Ydb : FeedbackStorage(FeedbackStorageType.YDB)
    data object Mattermost : FeedbackStorage(FeedbackStorageType.MATTERMOST)
    data class Notion(val dbToken: String) : FeedbackStorage(FeedbackStorageType.NOTION)
}
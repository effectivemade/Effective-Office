package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.utils.StringResource

data class LatestEventInfoUiState(
    val isError: Boolean,
    val errorText: StringResource,
    val isLoading: Boolean,
    val isData: Boolean,
    val isPlay: Boolean,
    val eventsInfo: List<StoryModel>,
    var currentStoryIndex: Int,
    val keySort: KeySortDuolingoUser = KeySortDuolingoUser.Xp,
    val storyProcess: Float
) {
    companion object {
        val empty = LatestEventInfoUiState(
            isError = false,
            errorText = StringResource.DynamicResource(""),
            isPlay = true,
            isLoading = true,
            isData = false,
            eventsInfo = listOf(),
            currentStoryIndex = -1,
            storyProcess = 1f
        )
    }
}

enum class KeySortDuolingoUser {
    Xp, Streak
}
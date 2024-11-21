package band.effective.office.tv.screen.leaderIdEvents

import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo

data class LeaderIdEventsUiState(
    val isLoading: Boolean,
    val isData: Boolean,
    val isError: Boolean,
    val eventsInfo: List<LeaderIdEventInfo>,
    val curentEvent: Int,
    val isPlay: Boolean,
    val errorText: String,
) {
    companion object {
        val empty = LeaderIdEventsUiState(
            isLoading = true,
            isData = false,
            isError = false,
            eventsInfo = listOf(),
            curentEvent = -1,
            isPlay = true,
            errorText = ""
        )
    }
}
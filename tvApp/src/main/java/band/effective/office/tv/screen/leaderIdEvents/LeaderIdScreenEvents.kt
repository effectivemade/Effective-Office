package band.effective.office.tv.screen.leaderIdEvents

sealed interface LeaderIdScreenEvents {
    object OnClickPlayButton : LeaderIdScreenEvents
    object OnClickNextItem : LeaderIdScreenEvents
    object OnClickPreviousItem : LeaderIdScreenEvents
}

package band.effective.office.tv.screen.eventStory

sealed interface EventStoryScreenEvents {
    object OnClickPlayButton : EventStoryScreenEvents
    object OnClickNextItem : EventStoryScreenEvents
    object OnClickPreviousItem : EventStoryScreenEvents
}
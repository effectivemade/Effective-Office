package band.effective.office.tablet.network.model

sealed interface WebServerEvent {
    object RoomInfoUpdate : WebServerEvent
    object OrganizerInfoUpdate : WebServerEvent
}
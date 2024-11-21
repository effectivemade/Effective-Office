package band.effective.office.tv.screen.message.primaryMessage

sealed class PrimaryMessageScreenEvents {
    object OnClickPlayButton: PrimaryMessageScreenEvents()
    object OnClickNextButton: PrimaryMessageScreenEvents()
    object OnClickPrevButton: PrimaryMessageScreenEvents()
}
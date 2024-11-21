package band.effective.office.tv.screen.message.primaryMessage

import band.effective.office.tv.domain.model.message.BotMessage

data class PrimaryMessageScreenState(
    val isEmpty: Boolean,
    val messagesList: List<BotMessage>,
    val currentMessage: Int,
    val isPlay: Boolean,
    val messageProcess: Float
) {
    companion object {
        val empty = PrimaryMessageScreenState(
            isEmpty = true,
            messagesList = listOf(),
            currentMessage = 0,
            isPlay = true,
            messageProcess = 1f
        )
    }
}

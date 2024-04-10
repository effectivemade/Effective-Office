package band.effective.office.tvServer.model

import java.time.LocalDateTime
data class SavedMessage(
    val message: MattermostMessage,
    val start: LocalDateTime,
    val finish: LocalDateTime
)

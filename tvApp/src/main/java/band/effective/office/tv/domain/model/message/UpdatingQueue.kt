package band.effective.office.tv.domain.model.message

/**Immutable queue of BotMessage*/
data class UpdatingQueue(val queue: List<BotMessage> = listOf()) {
    fun push(message: BotMessage): UpdatingQueue = copy(queue = queue + message)
    fun top(): BotMessage = queue.first()
    fun pop(): UpdatingQueue = copy(queue = queue - top())
}

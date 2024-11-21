package band.effective.office.tv.domain.model.message

import com.squareup.moshi.JsonClass
import java.util.GregorianCalendar

@JsonClass(generateAdapter = true)
data class BotMessage(
    val channelId: String = "",
    val author: User = User(),
    val id: String = "",
    val text: String = "",
    val start: GregorianCalendar = GregorianCalendar(),
    var finish: GregorianCalendar = GregorianCalendar(),
    val rootId: String = "",
    val directId: String = ""
) {
    companion object {
        /**List deleted and saved message*/
        val deletedMessage: MutableList<BotMessage> = mutableListOf()
        fun safeMessage(message: BotMessage){
            deletedMessage.add(message)
            if (deletedMessage.size > 10){
                deletedMessage.removeAt(0)
            }
        }
    }
}

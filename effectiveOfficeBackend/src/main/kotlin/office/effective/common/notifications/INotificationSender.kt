package office.effective.common.notifications

import io.ktor.http.*
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

/**
 * Interface for sending notifications on topics
 */
interface INotificationSender {

    /**
     * Sends message about topic modification
     */
    fun sendEmptyMessage(topic: String)

    /**
     * Sends message about workspace modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedWorkspace will be put as "object" in message data
     */
    fun sendContentMessage(action: HttpMethod, modifiedWorkspace: WorkspaceDTO)

    /**
     * Sends message about user modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedUser will be put as "object" in message data
     */
    fun sendContentMessage(action: HttpMethod, modifiedUser: UserDTO)

    /**
     * Sends message about booking modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedBooking will be put as "object" in message data
     */
    fun sendContentMessage(action: HttpMethod, modifiedBooking: BookingDTO)
}
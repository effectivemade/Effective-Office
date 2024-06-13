package band.effective.office.tablet

import android.util.Log
import band.effective.office.network.api.Collector
import band.effective.office.network.dto.fcm.FcmEventDTO
import band.effective.office.network.dto.fcm.FcmWorkspaceWithBookingsDTO
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ServerMessagingService
    : FirebaseMessagingService(), KoinComponent {
    private val collector: Collector<FcmWorkspaceWithBookingsDTO> by inject()

    private var currentWorkspaces = mutableListOf<FcmWorkspaceWithBookings>()

    override fun onMessageReceived(message: RemoteMessage) {
        processMessage(message)
    }

    private fun processMessage(message: RemoteMessage) {
        val workspace = message.toFcmWorkspace()
        if (workspace == null) {
            Log.i("ServerMessagingService", "Couldn't convert remote message to Fcm Workspace DTO")
            return
        }

        val isRelated = isRelatedToPreviousMessages(workspace)

        // in case some message was lost and sync wasn't finished
        if (!isRelated && currentWorkspaces.isNotEmpty()) {
            currentWorkspaces = mutableListOf()
        }

        currentWorkspaces.add(workspace)
        val shouldEnd = currentWorkspaces.size == workspace.maxMessages
        if (shouldEnd) return finishSync()
    }

    private fun List<FcmWorkspaceWithBookings>.toSingleWorkspace(): FcmWorkspaceWithBookingsDTO {
        val bookings = mutableListOf<FcmEventDTO>()
        for (workspace in this) {
            bookings.addAll(workspace.bookings)
        }
        val id = first().id
        return FcmWorkspaceWithBookingsDTO(
            id, bookings
        )
    }

    private fun finishSync() {
        val workspace = currentWorkspaces.toSingleWorkspace()
        collector.emit(workspace)
        currentWorkspaces = mutableListOf()
    }

    private fun RemoteMessage.toFcmWorkspace(): FcmWorkspaceWithBookings? {
        val messageId = data["id"] ?: return null
        val maxMessages = data["maxMessages"]?.toIntOrNull() ?: return null
        val messageNum = data["messageNum"]?.toIntOrNull() ?: return null
        val workspaceDto = data["workspace"]?.let {
            Json.decodeFromString<FcmWorkspaceWithBookingsDTO>(it)
        } ?: return null

        return workspaceDto.toWorkspace(messageId, maxMessages, messageNum)
    }

    private fun isRelatedToPreviousMessages(workspace: FcmWorkspaceWithBookings) =
        currentWorkspaces.isNotEmpty()
                && currentWorkspaces.all {
                    it.messageId == workspace.messageId
                            && it.id == workspace.id
                }

    data class FcmWorkspaceWithBookings(
        val id: String,
        val messageId: String,
        val messageNum: Int,
        val maxMessages: Int,
        val bookings: List<FcmEventDTO>
    )

    private fun FcmWorkspaceWithBookingsDTO.toWorkspace(
        messageId: String,
        messageNum: Int,
        maxMessages: Int,
    ) = FcmWorkspaceWithBookings(
        id = id,
        messageId = messageId,
        messageNum = messageNum,
        maxMessages = maxMessages,
        bookings = bookings
    )
}
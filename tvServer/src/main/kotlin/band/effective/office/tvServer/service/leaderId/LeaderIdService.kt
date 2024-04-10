package band.effective.office.tvServer.service.leaderId

import band.effective.office.tvServer.model.LeaderIdEventInfo
import band.effective.office.tvServer.repository.leaderId.LeaderRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LeaderIdService(private val leaderRepository: LeaderRepository) {
    suspend fun getEvents(
        finishDate: LocalDate? = null,
        cityId: Int? = null,
        placeId: Int? = null
    ): Flow<LeaderIdEventInfo> = leaderRepository.getEventsInfo(
        finishDate = finishDate ?: LocalDate.now().plusDays(31),
        cityId = cityId ?: 893,
        placeId = placeId ?: 3942
    )

    suspend fun getEvents(
        finishDate: String?,
        cityId: String?,
        placeId: String?
    ): Flow<LeaderIdEventInfo> =
        getEvents(
            finishDate = finishDate?.run {
                try {
                    LocalDate.parse(finishDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                } catch (e: Exception) {
                    null
                }
            },
            cityId = cityId?.toIntOrNull(),
            placeId = placeId?.toIntOrNull()
        )

}
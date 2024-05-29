package band.effective.office.tvServer.repository.leaderId

import band.effective.office.tvServer.model.LeaderIdEventInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LeaderRepository {
    suspend fun getEventsInfo(finishDate: LocalDate, cityId: Int, placeId: Int): Flow<LeaderIdEventInfo>
}
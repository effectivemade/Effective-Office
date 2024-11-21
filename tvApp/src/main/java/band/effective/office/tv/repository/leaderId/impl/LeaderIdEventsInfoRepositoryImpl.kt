package band.effective.office.tv.repository.leaderId.impl

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.domain.model.leaderId.toLeaderIdEventInfo
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.repository.leaderId.LeaderIdEventsInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import javax.inject.Inject

class LeaderIdEventsInfoRepositoryImpl @Inject constructor(val leaderApi: LeaderApi) :
    LeaderIdEventsInfoRepository {
    override suspend fun getEventsInfo(finishDate: GregorianCalendar, cityId: Int, placeId: Int): Flow<Either<String, LeaderIdEventInfo>> =
        flow{
            when (val either = leaderApi.searchEvents(100, getDate(GregorianCalendar()),getDate(finishDate), cityId, placeId)) {
                is Either.Success -> {
                    val ids = either.data.data.items.map { it.id }
                    ids.forEach {
                        emit(
                            when (val apiResponse = leaderApi.eventInfo(it)){
                            is Either.Success -> Either.Success(apiResponse.data.toLeaderIdEventInfo())
                            is Either.Failure -> Either.Success(apiResponse.error.toLeaderIdEventInfo())
                        })
                    }
                }
                is Either.Failure -> emit(Either.Failure(either.error.message))
            }
        }

}
private fun ErrorReason.toLeaderIdEventInfo(): LeaderIdEventInfo =
    LeaderIdEventInfo(message)

fun getDate(calendar: GregorianCalendar): String = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)







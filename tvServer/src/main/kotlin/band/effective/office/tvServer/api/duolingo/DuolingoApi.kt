package band.effective.office.tvServer.api.duolingo

import band.effective.office.tvServer.api.duolingo.models.DuolingoResponse

interface DuolingoApi {
    suspend fun getUser(username:String): DuolingoResponse
}
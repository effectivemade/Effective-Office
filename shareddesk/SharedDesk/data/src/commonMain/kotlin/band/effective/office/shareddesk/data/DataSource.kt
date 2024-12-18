package band.effective.office.shareddesk.data

import band.effective.office.shareddesk.data.models.WorkplaceDto

interface DataSource {
    suspend fun getOfficeSourceMap(): Result<ByteArray>
    suspend fun saveOfficeSourceMap(byteArray: ByteArray): Result<Boolean>
    suspend fun getWorkplaces(): Result<List<WorkplaceDto>>
    suspend fun addNewWorkplace(worplace: WorkplaceDto): Result<Boolean>
}
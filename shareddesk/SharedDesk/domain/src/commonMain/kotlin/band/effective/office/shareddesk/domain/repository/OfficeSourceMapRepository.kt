package band.effective.office.shareddesk.domain.repository

import band.effective.office.shareddesk.domain.models.Workplace
import band.effective.office.svgparser.CanvasData

interface OfficeSourceMapRepository {

    suspend fun getSourceMap(): Result<ByteArray>

    suspend fun saveMap(canvasData: CanvasData): Result<Boolean>

    suspend fun getWorkplaces(): Result<List<Workplace>>
    
    suspend fun addNewWorkplace(workplace: Workplace): Result<Boolean>
}
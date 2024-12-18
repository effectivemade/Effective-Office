package band.effective.office.shareddesk.domain.repository.impl

import band.effective.office.shareddesk.data.DataSource
import band.effective.office.shareddesk.domain.mapper.WorkplacesDomainMapper
import band.effective.office.shareddesk.domain.mapper.WorkplacesDtoMapper
import band.effective.office.shareddesk.domain.models.Workplace
import band.effective.office.shareddesk.domain.repository.OfficeSourceMapRepository
import band.effective.office.svgparser.CanvasData
import band.effective.office.svgparser.CanvasDataParser

internal class OfficeSourceMapRepositoryImpl(
    private val dataSource: DataSource,
    private val canvasDataParser: CanvasDataParser,
    private val workplacesDomainMapper: WorkplacesDomainMapper,
    private val workplacesDtoMapper: WorkplacesDtoMapper,
) : OfficeSourceMapRepository {
    override suspend fun getSourceMap(): Result<ByteArray> {
        return dataSource.getOfficeSourceMap()
    }

    override suspend fun saveMap(canvasData: CanvasData): Result<Boolean> {
        val svg = canvasDataParser.parseToSvg(canvasData)
        return dataSource.saveOfficeSourceMap(svg.encodeToByteArray())
    }

    override suspend fun getWorkplaces(): Result<List<Workplace>> {
        return dataSource.getWorkplaces().map { it.map(workplacesDomainMapper::map) }
    }

    override suspend fun addNewWorkplace(workplace: Workplace): Result<Boolean> {
        return dataSource.addNewWorkplace(workplacesDtoMapper.map(workplace))
    }
}
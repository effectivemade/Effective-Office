package band.effective.office.shareddesk.interactivemap.presentation

import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi
import band.effective.office.svgparser.model.MapPathData
import band.effective.office.svgparser.model.Rectangle

data class OfficeMapState(
    val workplaces: List<WorkplaceUi>,
    val mapPaths: List<MapPathData>,
    val background: Rectangle? = null,
    val width: Int,
    val height: Int,
    val viewBox: String,
)
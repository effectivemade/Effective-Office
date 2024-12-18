package band.effective.office.svgparser

import band.effective.office.svgparser.model.Group
import band.effective.office.svgparser.model.MapPathData
import band.effective.office.svgparser.model.Rectangle

data class CanvasData(
    val rects: List<Rectangle>,
    val mapPathData: List<MapPathData>,
    val groups: List<Group>,
    val width: String,
    val height: String,
    val viewBox: String,
)
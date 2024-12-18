package band.effective.office.svgparser.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Immutable
data class MapPathData(
    val id: String?,
    val path: Path,
    val strokeColor: Color,
    val strokeWidth: Int,
    val fill: Color?,
    val boundingBox: Rect
)
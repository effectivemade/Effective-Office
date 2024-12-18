package band.effective.office.shareddesk.interactivemap.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntSize
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapConstants.WORKPLACE_ACTIVE_INDICATION_RADIUS
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapConstants.workplaceAreaFocusedBorderColor
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapConstants.workplaceAreaFocusedColor
import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi

internal fun DrawScope.drawMapRooms(state: OfficeMapState) {
    state.mapPaths.filter { it.fill == null }.forEach { statePath ->
        drawPath(
            path = statePath.path,
            color = statePath.strokeColor,
            style = Stroke(width = statePath.strokeWidth.toFloat())
        )
    }
    state.mapPaths.filter { it.fill != null }.forEach {
        drawPath(
            path = it.path,
            color = it.fill!!,
            style = Fill
        )
    }
}

internal fun DrawScope.drawMapBackground(state: OfficeMapState, screenSize: IntSize) {
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width
    state.background?.let {
        drawRect(
            color = it.fill ?: Color.Blue,
            size = Size(screenWidth.toFloat(), screenHeight.toFloat()),
        )
    }
}

internal fun List<WorkplaceUi>.asRectList(): List<Rect> {
    return map {
        Rect(
            offset = Offset(it.x, it.y),
            size = Size(it.width, it.height)
        )
    }
}

fun DrawScope.drawActiveWorkspaceIndication(
    topLeftOffset: Offset,
    size: Size
) {
    drawCircle(
        color = workplaceAreaFocusedColor,
        center = Offset(
            x = topLeftOffset.x + size.width / 2,
            y = topLeftOffset.y + size.height / 2,
        ),
        radius = WORKPLACE_ACTIVE_INDICATION_RADIUS,
    )
    drawCircle(
        color = workplaceAreaFocusedBorderColor,
        center = Offset(
            x = topLeftOffset.x + size.width / 2,
            y = topLeftOffset.y + size.height / 2,
        ),
        style = Stroke(1f),
        radius = WORKPLACE_ACTIVE_INDICATION_RADIUS,
    )
}
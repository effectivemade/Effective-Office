package band.effective.office.shareddesk.interactivemap.presentation.mapper

import androidx.compose.ui.graphics.Color
import band.effective.office.shareddesk.domain.models.Workplace
import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi
import band.effective.office.svgparser.CanvasData
import band.effective.office.svgparser.model.Group
import band.effective.office.svgparser.model.Rectangle

internal class CanvasDataUiMapper {
    companion object {
        private const val BACKGROUND_RECT_ID = "background"
    }

    fun map(listGroup: List<Group>, workplaces: List<Workplace>): List<WorkplaceUi> {
        return listGroup.map { groupItem ->
            WorkplaceUi(
                x = groupItem.rects.first().x ?: 0f,
                y = groupItem.rects.first().y ?: 0f,
                color = groupItem.rects.first().fill ?: Color.Black,
                rx = groupItem.rects.first().rx,
                width = groupItem.rects.first().width,
                height = groupItem.rects.first().height,
                id = groupItem.id,
                name = groupItem.rects.first().id.toString(),
                isBusy = workplaces.find { it.id == groupItem.id }?.isBusy ?: false,
                isNew = false,
            )
        }.filter { groupItem -> groupItem.rx != null }
    }

    fun findBackground(canvasData: CanvasData): Rectangle? = canvasData.rects.find { it.id == BACKGROUND_RECT_ID }

}
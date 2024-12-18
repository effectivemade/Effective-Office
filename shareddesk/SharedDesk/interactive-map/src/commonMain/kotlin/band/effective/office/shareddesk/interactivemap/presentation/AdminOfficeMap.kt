package band.effective.office.shareddesk.interactivemap.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapConstants.workplaceBusyColor
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapConstants.workplaceFocusedColor
import band.effective.office.shareddesk.interactivemap.presentation.views.ControlPanel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AdminOfficeMap() {
    val viewModel: AdminOfficeMapViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    var currentDraggableIndex by remember { mutableStateOf<Int?>(null) }
    var currentSelectedIndex by remember { mutableStateOf<Int?>(null) }
    var xOffset by remember { mutableStateOf(0f) }
    var yOffset by remember { mutableStateOf(0f) }
    val textMeasurer = rememberTextMeasurer()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .height(550.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .border(1.dp, Color.Gray)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentSelectedIndex = null
                                state.workplaces.asRectList().forEachIndexed { index, rect ->
                                    if (rect.contains(offset)) {
                                        currentDraggableIndex = index
                                        return@forEachIndexed
                                    }
                                }
                            },
                            onDragEnd = {
                                currentDraggableIndex?.let { index ->
                                    viewModel.moveWorkPlace(index, xOffset, yOffset)
                                }
                                currentDraggableIndex = null
                                xOffset = 0f
                                yOffset = 0f
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            xOffset += dragAmount.x
                            yOffset += dragAmount.y
                        }
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { offset ->
                                state.workplaces.asRectList().forEachIndexed { index, rect ->
                                    if (rect.contains(offset)) {
                                        currentSelectedIndex = index
                                        return@forEachIndexed
                                    }
                                }
                            },
                        )
                    }
            ) {
                drawMapBackground(state, this.size.toIntSize())
                drawMapRooms(state)
                drawWorkplaces(
                    state = state,
                    currentDraggableIndex = currentDraggableIndex,
                    xOffset = xOffset,
                    yOffset = yOffset,
                    currentSelectedIndex = currentSelectedIndex,
                    textMeasurer = textMeasurer,
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        ControlPanel(
            modifier = Modifier,
            onSaveClick = viewModel::save,
            onAddClick = viewModel::addNewWorkplace,
            onRemoveClick = {
                viewModel.removeWorkplace(currentSelectedIndex)
                currentSelectedIndex = null
            },
            onRefreshClick = viewModel::refresh,
        )
    }
}

private fun DrawScope.drawWorkplaces(
    state: OfficeMapState,
    currentDraggableIndex: Int?,
    currentSelectedIndex: Int?,
    xOffset: Float,
    yOffset: Float,
    textMeasurer: TextMeasurer,
) {
    state.workplaces.forEachIndexed { index, workplaceUi ->
        val width = workplaceUi.width
        val height = workplaceUi.height
        val roundCoeff = workplaceUi.rx ?: 16f
        val topLeftOffset = Offset(
            x = workplaceUi.x + if (currentDraggableIndex == index) xOffset else 0f,
            y = workplaceUi.y + if (currentDraggableIndex == index) yOffset else 0f,
        )
        val size = Size(width, height)
        drawRoundRect(
            color = when {
                currentSelectedIndex == index -> workplaceFocusedColor
                workplaceUi.isBusy -> workplaceBusyColor
                else -> workplaceUi.color
            },
            topLeft = topLeftOffset,
            size = size,
            cornerRadius = CornerRadius(x = roundCoeff, y = roundCoeff),
        )

        if (currentSelectedIndex == index) drawActiveWorkspaceIndication(topLeftOffset, size)

        val textLayoutResult = textMeasurer.measure(
            text = workplaceUi.name,
            style = TextStyle(fontSize = TextUnit(10f, TextUnitType.Sp)),
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = topLeftOffset.x + (width / 2) - textLayoutResult.size.width / 2,
                y = topLeftOffset.y + (height / 2) - textLayoutResult.size.height / 2,
            ),
        )
    }
}

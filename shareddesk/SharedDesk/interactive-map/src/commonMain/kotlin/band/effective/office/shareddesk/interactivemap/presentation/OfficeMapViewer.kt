package band.effective.office.shareddesk.interactivemap.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
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
import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OfficeMapViewer() {
    val viewModel: OfficeMapViewerViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    var currentSelectedIndex by remember { mutableStateOf<Int?>(null) }
    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val minScale = 0.5f
    val maxScale = 3f

    var workplaceInfo by remember { mutableStateOf<WorkplaceUi?>(null) }
    var currentSelectedWorkplaceRoom by remember { mutableStateOf<String?>(null) }

    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(currentSelectedIndex) {
        viewModel.showWorkplaceInfo(currentSelectedIndex)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is OfficeMapEffect.ShowWorkplaceInfoModal -> workplaceInfo = effect.workplaceUi
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .border(1.dp, Color.Gray)
                    .graphicsLayer(
                        scaleX = zoom,
                        scaleY = zoom,
                        translationX = offsetX,
                        translationY = offsetY,
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures(
                            onGesture = { _, pan, gestureZoom, _ ->
                                currentSelectedIndex = null
                                zoom = (zoom * gestureZoom).coerceIn(minScale, maxScale)
                                if (zoom > 1) {
                                    offsetX += pan.x * zoom
                                    offsetY += pan.y * zoom
                                } else {
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            }
                        )
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
                                state.mapPaths.forEach {
                                    if (it.boundingBox.contains(offset)) {
                                        currentSelectedWorkplaceRoom = it.id
                                    }
                                }
                            },
                        )
                    }
            ) {
                drawMapBackground(state, this.size.toIntSize())
                drawMapRooms(state)
                drawWorkplaces(state, currentSelectedIndex, textMeasurer)
            }
        }
    }


    workplaceInfo?.let { workplace ->
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = {
                currentSelectedIndex = null
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                ) {
                    Text("Room: ${currentSelectedWorkplaceRoom}")
                    Text("Workplace: ${workplace.name}")
                    Text("Busy: ${workplace.isBusy}")

                    Spacer(Modifier.height(32.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        enabled = !workplace.isBusy,
                    ) {
                        Text("Book")
                    }
                }
            }
        )
    }
}

private fun DrawScope.drawWorkplaces(
    state: OfficeMapState,
    currentSelectedIndex: Int?,
    textMeasurer: TextMeasurer,
) {
    state.workplaces.forEachIndexed { index, workplaceUi ->
        val width = workplaceUi.width
        val height = workplaceUi.height
        val roundCoeff = workplaceUi.rx ?: 16f
        val topLeftOffset = Offset(
            x = workplaceUi.x,
            y = workplaceUi.y,
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
            style = TextStyle(fontSize = TextUnit(5f, TextUnitType.Sp)),
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
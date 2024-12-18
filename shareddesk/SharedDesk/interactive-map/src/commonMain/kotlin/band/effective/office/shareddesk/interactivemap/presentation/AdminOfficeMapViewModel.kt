package band.effective.office.shareddesk.interactivemap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.shareddesk.domain.models.Workplace
import band.effective.office.shareddesk.domain.repository.OfficeSourceMapRepository
import band.effective.office.shareddesk.interactivemap.presentation.mapper.CanvasDataUiMapper
import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi
import band.effective.office.svgparser.CanvasData
import band.effective.office.svgparser.SvgParser
import band.effective.office.svgparser.model.Group
import band.effective.office.svgparser.model.Rectangle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class AdminOfficeMapViewModel(
    private val repository: OfficeSourceMapRepository,
    private val svgParser: SvgParser,
    private val canvasDataUiMapper: CanvasDataUiMapper,
) : ViewModel() {
    private val mutableState = MutableStateFlow(
        OfficeMapState(
            workplaces = emptyList(),
            mapPaths = emptyList(),
            width = 0,
            height = 0,
            viewBox = "",
        )
    )
    val state: StateFlow<OfficeMapState> = mutableState.asStateFlow()
    val effect: SharedFlow<OfficeMapEffect>
        get() = TODO("Not yet implemented")

    init {
        initialLoad()
    }

    fun moveWorkPlace(currentDraggableIndex: Int, xOffset: Float, yOffset: Float) {
        mutableState.update {
            val workPlacesMutable = it.workplaces.toMutableList()
            val currentWorkPlace = workPlacesMutable[currentDraggableIndex]
            workPlacesMutable.removeAt(currentDraggableIndex)
            workPlacesMutable.add(
                currentWorkPlace.copy(
                    x = currentWorkPlace.x + xOffset,
                    y = currentWorkPlace.y + yOffset,
                )
            )
            it.copy(
                workplaces = workPlacesMutable,
            )
        }
    }

    fun save() = viewModelScope.launch {
        repository.saveMap(
            CanvasData(
                rects = listOfNotNull(state.value.background),
                mapPathData = state.value.mapPaths,
                width = state.value.width.toString(),
                height = state.value.height.toString(),
                viewBox = state.value.viewBox,
                groups = state.value.workplaces.toCanvasGroupList(),
            )
        ).onSuccess {
            state.value.workplaces.filter { it.isNew }.forEach {
                repository.addNewWorkplace(
                    Workplace(
                        id = it.id,
                        isBusy = it.isBusy,
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addNewWorkplace() = viewModelScope.launch {
        val workPlaceId = Uuid.random().toString()
        val workPlaceName = Random.nextInt(from = 1, 10).toString() // TODO create id by parent room
        mutableState.update {
            val workplaces = it.workplaces.toMutableList()
            workplaces.add(
                OfficeMapConstants.getWorkplaceSketch(
                    id = workPlaceId,
                    name = workPlaceName,
                )
            )
            it.copy(
                workplaces = workplaces
            )
        }
    }

    fun removeWorkplace(currentSelectedIndex: Int?) {
        if (currentSelectedIndex == null) return
        mutableState.update {
            val workplaces = it.workplaces.toMutableList()
            workplaces.removeAt(currentSelectedIndex)
            it.copy(
                workplaces = workplaces
            )
        }
    }

    fun refresh() = initialLoad()

    private fun initialLoad() {
        viewModelScope.launch {
            val svgSource = repository.getSourceMap().getOrNull() ?: return@launch
            val canvasData = svgParser.parse(svgSource)
            val workplaces = repository.getWorkplaces().getOrNull() ?: return@launch
            mutableState.update {
                it.copy(
                    workplaces = canvasDataUiMapper.map(canvasData.groups, workplaces),
                    mapPaths = canvasData.mapPathData,
                    background = canvasDataUiMapper.findBackground(canvasData),
                    width = canvasData.width.toInt(),
                    height = canvasData.height.toInt(),
                    viewBox = canvasData.viewBox,
                )
            }
        }
    }

    private fun List<WorkplaceUi>.toCanvasGroupList(): List<Group> {
        return map {
            Group(
                id = it.id,
                rects = listOf(
                    Rectangle(
                        id = it.name,
                        width = it.width,
                        height = it.height,
                        fill = it.color,
                        rx = it.rx,
                        x = it.x,
                        y = it.y,
                    )
                ),
            )
        }
    }
}
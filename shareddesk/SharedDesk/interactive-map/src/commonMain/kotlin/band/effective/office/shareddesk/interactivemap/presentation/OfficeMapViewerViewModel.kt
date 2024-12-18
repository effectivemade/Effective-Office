package band.effective.office.shareddesk.interactivemap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.shareddesk.domain.repository.OfficeSourceMapRepository
import band.effective.office.shareddesk.interactivemap.presentation.mapper.CanvasDataUiMapper
import band.effective.office.svgparser.SvgParser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class OfficeMapViewerViewModel(
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

    private val mutableEffect = MutableSharedFlow<OfficeMapEffect>()
    val effect: SharedFlow<OfficeMapEffect> = mutableEffect.asSharedFlow()

    init {
        initialLoad()
    }

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

    fun showWorkplaceInfo(currentSelectedIndex: Int?) = viewModelScope.launch {
        mutableEffect.emit(OfficeMapEffect.ShowWorkplaceInfoModal(
            currentSelectedIndex?.let { state.value.workplaces[it] }
        ))
    }
}
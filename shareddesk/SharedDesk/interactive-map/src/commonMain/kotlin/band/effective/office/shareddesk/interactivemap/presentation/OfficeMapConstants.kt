package band.effective.office.shareddesk.interactivemap.presentation

import androidx.compose.ui.graphics.Color
import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi


internal object OfficeMapConstants {

    val workplaceFocusedColor = Color(0xFF8F49E9)
    val workplaceAreaFocusedBorderColor = Color(0xFF8F49E9)
    val workplaceAreaFocusedColor = Color(0x208F49E9)
    val workplaceBusyColor = Color(0xFFEBEBEB)

    const val WORKPLACE_ACTIVE_INDICATION_RADIUS = 70f
    private const val WORKPLACE_INITIAL_X_POX = 0f
    private const val WORKPLACE_INITIAL_Y_POX = 0f
    private const val WORKPLACE_INITIAL_SIZE = 36f
    private const val WORKPLACE_INITIAL_RECT_ROUND = WORKPLACE_INITIAL_SIZE / 2
    private val workplaceDefaultColor = Color(0xFFF1E8FC)

    fun getWorkplaceSketch(id: String, name: String) = WorkplaceUi(
        id = id,
        x = WORKPLACE_INITIAL_X_POX,
        y = WORKPLACE_INITIAL_Y_POX,
        width = WORKPLACE_INITIAL_SIZE,
        height = WORKPLACE_INITIAL_SIZE,
        color = workplaceDefaultColor,
        rx = WORKPLACE_INITIAL_RECT_ROUND,
        name = name,
        isBusy = false,
        isNew = true,
    )
}
package band.effective.office.shareddesk.interactivemap.presentation.models

import androidx.compose.ui.graphics.Color

data class WorkplaceUi(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Color,
    val rx: Float?,
    val id: String,
    val name: String,
    val isBusy: Boolean,
    val isNew: Boolean,
)

package band.effective.office.svgparser.model

import androidx.compose.ui.graphics.Color

data class Rectangle(
    val id: String?,
    val width: Float,
    val height: Float,
    val fill: Color?,
    val rx: Float?,
    val x: Float? = null,
    val y: Float? = null,
)
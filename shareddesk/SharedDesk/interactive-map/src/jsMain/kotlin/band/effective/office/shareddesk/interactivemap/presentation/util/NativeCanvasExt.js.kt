package band.effective.office.shareddesk.interactivemap.presentation.util

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.*

actual fun DrawScope.drawNativeText(
    text: String,
    x: Float,
    y: Float,
    fontSizePx: Float,
) {
    val paint = Paint().apply {
        this.strokeWidth = 5f
        this.color = Color.RED
    }
    val font = Font(Typeface.makeEmpty()).apply {
        edging = FontEdging.SUBPIXEL_ANTI_ALIAS
        size = 100f
    }
    val textLine = TextLine.make(
        text,
        font,
    )

    drawIntoCanvas {
        it.nativeCanvas.drawTextLine(
            line = textLine,
            x = x - textLine.width / 2,
            y = y + textLine.capHeight / 2,
            paint = paint,
        )
    }
}
package band.effective.office.shareddesk.interactivemap.presentation.util

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.TextLine

actual fun DrawScope.drawNativeText(
    text: String,
    x: Float,
    y: Float,
    fontSizePx: Float,
) {
    val paint = org.jetbrains.skia.Paint()
    val textLine = TextLine.make(text, Font(null, fontSizePx))
    drawIntoCanvas {
        it.nativeCanvas.drawTextLine(
            TextLine.make(text, Font(null, fontSizePx)),
            x = x - textLine.width / 2,
            y = y + textLine.height / 2,
            paint = paint,
        )
    }
}
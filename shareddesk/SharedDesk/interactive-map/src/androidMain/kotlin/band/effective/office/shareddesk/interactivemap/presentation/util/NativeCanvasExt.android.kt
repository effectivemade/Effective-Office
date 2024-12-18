package band.effective.office.shareddesk.interactivemap.presentation.util

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.Font

actual fun DrawScope.drawNativeText(text: String, x: Float, y: Float, fontSizePx: Float) {
    val paint = android.graphics.Paint().apply {
        this.textSize = fontSizePx
    }
    val width = paint.measureText(text)
    drawIntoCanvas {
        it.nativeCanvas.drawText(
            text,
            x - width / 2,
            y + width / 2,
            paint,
        )
    }
}
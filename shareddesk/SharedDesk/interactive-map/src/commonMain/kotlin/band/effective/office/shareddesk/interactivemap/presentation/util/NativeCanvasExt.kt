package band.effective.office.shareddesk.interactivemap.presentation.util

import androidx.compose.ui.graphics.drawscope.DrawScope

expect fun DrawScope.drawNativeText(text: String, x: Float, y: Float, fontSizePx: Float)
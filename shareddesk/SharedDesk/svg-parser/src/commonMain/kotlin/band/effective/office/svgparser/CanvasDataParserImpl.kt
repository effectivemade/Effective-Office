package band.effective.office.svgparser

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toSvg
import band.effective.office.svgparser.model.*
import kotlinx.serialization.encodeToString
import kotlin.math.roundToInt

internal class CanvasDataParserImpl : CanvasDataParser {

    override fun parseToSvg(canvasData: CanvasData): String {
        val svg = Svg(
            rects = canvasData.rects.mapToSvgRect(),
            nodes = canvasData.mapPathData.mapToSvgPath(),
            width = canvasData.width,
            height = canvasData.height,
            viewBox = canvasData.viewBox,
            gList = canvasData.groups.map {
                G(
                    id = it.id,
                    rects = it.rects.mapToSvgRect(),
                    nodes = emptyList(),
                )
            }
        )
        return xmlSerializer.encodeToString<Svg>(svg).removeEmptyXmlns()
    }

    private fun List<Rectangle>.mapToSvgRect(): List<Rect> {
        return map {
            Rect(
                id = it.id,
                x = it.x?.toString(),
                y = it.y?.toString(),
                width = it.width.toString(),
                height = it.height.toString(),
                fill = it.fill?.toHex(),
                rx = it.rx?.roundToInt(),
            )
        }
    }

    private fun List<MapPathData>.mapToSvgPath(): List<PathData> {
        return map {
            PathData(
                id = it.id,
                d = it.path.toSvg(),
                fill = it.fill?.toHex(),
                strokeWidth = it.strokeWidth.toString(),
                stroke = it.strokeColor.toHex()
            )
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun Color.toHex(): String = "#" + toArgb().toHexString(HexFormat.Default).takeLast(6)

    private fun String.removeEmptyXmlns(): String = replace("xmlns=\"\"",  "")
}
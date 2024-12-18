package band.effective.office.svgparser

import PathParser
import androidx.compose.ui.graphics.Color
import band.effective.office.svgparser.model.*
import kotlinx.serialization.decodeFromString

class SvgParserImpl : SvgParser {
    override fun parse(sourceSvg: ByteArray): CanvasData {
        val svgString = sourceSvg.decodeToString()
        val svg = xmlSerializer.decodeFromString<Svg>(svgString)
        return decomposeSvg(svg)
    }

    private fun decomposeSvg(svg: Svg): CanvasData {
        val rectangle = svg.rects.map { it.toCanvasRectangle() }
        val mapPathData = svg.nodes.toMapPathDataList()
        val groups = svg.gList.toGroups()
        return CanvasData(
            width = svg.width,
            height = svg.height,
            viewBox = svg.viewBox,
            rects = rectangle,
            mapPathData = mapPathData,
            groups = groups,
        )
    }

    private fun String.asColor(): Color {
        return Color("ff${this.removePrefix("#").lowercase()}".toLong(16))
    }

    private fun List<G>.toGroups(): List<Group> {
        return map { it ->
            Group(
                id = it.id,
                rects = it.rects.map { it.toCanvasRectangle() },
            )
        }
    }


    private fun Rect.toCanvasRectangle(): Rectangle {
        return Rectangle(
            id = id,
            width = width?.toFloat()!!,
            height = height?.toFloat()!!,
            fill = fill?.asColor(),
            rx = rx?.toFloat(),
            x = x?.toFloat(),
            y = y?.toFloat(),
        )
    }

    private fun List<PathData>.toMapPathDataList(): List<MapPathData> {
        return map { pathData ->
            val composePath = PathParser.createPathFromPathData(pathData.d)
            val bounds = composePath.getBounds()
            MapPathData(
                id = pathData.id,
                strokeColor = pathData.stroke?.asColor() ?: Color.Blue,
                path = composePath,
                boundingBox = bounds,
                strokeWidth = pathData.strokeWidth?.toInt() ?: 0,
                fill = pathData.fill?.asColor(),
            )
        }
    }
}
package band.effective.office.svgparser

interface CanvasDataParser {

    fun parseToSvg(canvasData: CanvasData): String
}
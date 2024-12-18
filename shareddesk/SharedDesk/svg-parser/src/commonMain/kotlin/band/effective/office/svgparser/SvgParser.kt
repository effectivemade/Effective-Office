package band.effective.office.svgparser

interface SvgParser {
    fun parse(sourceSvg: ByteArray): CanvasData
}
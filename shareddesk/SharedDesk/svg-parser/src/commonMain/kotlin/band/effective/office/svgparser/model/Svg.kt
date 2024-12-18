package band.effective.office.svgparser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("svg")
data class Svg(
    val rects: List<Rect>,
    val nodes: List<PathData>,
    val width: String,
    val height: String,
    val viewBox: String,
    val fill: String = "none",
    val xmlns: String = "http://www.w3.org/2000/svg",
    val circles: List<Circle> = emptyList(),
    val gList: List<G>
)

@Serializable
@SerialName("g")
data class G(
    val id: String,
    val rects: List<Rect>,
    val nodes: List<PathData>,
)

@Serializable
@SerialName("path")
data class PathData(
    @SerialName("id")
    val id: String?,
    @SerialName("d")
    val d: String,
    @SerialName("fill")
    val fill: String? = null,
    @SerialName("stroke")
    val stroke: String? = null,
    @SerialName("stroke-width")
    val strokeWidth: String? = null,
)

@Serializable
@SerialName("rect")
data class Rect(
    val id: String? = null,
    val x: String? = null,
    val y: String? = null,
    val width: String? = null,
    val height: String? = null,
    val fill: String? = null,
    val rx: Int? = null,
)

@Serializable
@SerialName("circle")
data class Circle(
    val r: String,
    val cx: String,
    val cy: String,
    val fill: String,
)

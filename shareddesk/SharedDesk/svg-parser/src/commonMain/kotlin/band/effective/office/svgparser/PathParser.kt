import androidx.compose.ui.graphics.Path
import band.effective.office.svgparser.exception.ArrayIndexOutOfBoundsException
import kotlin.math.*

// This class is a duplicate from the PathParser.java of frameworks/base, with slight
// update on incompatible API like copyOfRange().
/**
 * Parses SVG path strings.
 */
object PathParser {
    private const val LOGTAG = "PathParser"

    // Copy from Arrays.copyOfRange() which is only available from API level 9.
    /**
     * Copies elements from `original` into a new array, from indexes start (inclusive) to
     * end (exclusive). The original order of elements is preserved.
     * If `end` is greater than `original.length`, the result is padded
     * with the value `0.0f`.
     *
     * @param original the original array
     * @param start    the start index, inclusive
     * @param end      the end index, exclusive
     * @return the new array
     * @throws ArrayIndexOutOfBoundsException if `start < 0 || start > original.length`
     * @throws IllegalArgumentException       if `start > end`
     * @throws NullPointerException           if `original == null`
     */
    fun copyOfRange(original: FloatArray, start: Int, end: Int): FloatArray {
        require(start <= end)
        val originalLength = original.size
        if (start < 0 || start > originalLength) {
            throw ArrayIndexOutOfBoundsException()
        }
        val resultLength = end - start
        val copyLength = min(resultLength.toDouble(), (originalLength - start).toDouble()).toInt()
        var result = original.copyOfRange(start, copyLength)
        return result
    }

    /**
     * Takes a string representation of an SVG path and converts it to a [Path].
     *
     * @param pathData The string representing a path, the same as "d" string in svg file.
     * @return the generated Path object.
     */
    fun createPathFromPathData(pathData: String): Path {
        val path = Path()
        val nodes = createNodesFromPathData(pathData)
        try {
            PathDataNode.nodesToPath(nodes, path)
        } catch (e: RuntimeException) {
            throw RuntimeException("Error in parsing $pathData", e)
        }
        return path
    }

    /**
     * @param pathData The string representing a path, the same as "d" string in svg file.
     * @return an array of the PathDataNode.
     */
    fun createNodesFromPathData(pathData: String): Array<PathDataNode> {
        var start = 0
        var end = 1

        val list = ArrayList<PathDataNode>()
        while (end < pathData.length) {
            end = nextStart(pathData, end)
            val s = pathData.substring(start, end).trim { it <= ' ' }
            if (!s.isEmpty()) {
                val `val` = getFloats(s)
                addNode(list, s[0], `val`)
            }

            start = end
            end++
        }
        if ((end - start) == 1 && start < pathData.length) {
            addNode(list, pathData[start], FloatArray(0))
        }
        return list.toTypedArray<PathDataNode>()
    }

    /**
     * @param source The array of PathDataNode to be duplicated.
     * @return a deep copy of the `source`.
     */
    fun deepCopyNodes(
        source: Array<PathDataNode>
    ): Array<PathDataNode?> {
        val copy = arrayOfNulls<PathDataNode>(source.size)
        for (i in source.indices) {
            copy[i] = PathDataNode(source[i])
        }
        return copy
    }

    /**
     * @param nodesFrom The source path represented in an array of PathDataNode
     * @param nodesTo   The target path represented in an array of PathDataNode
     * @return whether the `nodesFrom` can morph into `nodesTo`
     */
    fun canMorph(
        nodesFrom: Array<PathDataNode>?,
        nodesTo: Array<PathDataNode>?
    ): Boolean {
        if (nodesFrom == null || nodesTo == null) {
            return false
        }

        if (nodesFrom.size != nodesTo.size) {
            return false
        }

        for (i in nodesFrom.indices) {
            if (nodesFrom[i].type != nodesTo[i].type
                || nodesFrom[i].params.size != nodesTo[i].params.size
            ) {
                return false
            }
        }
        return true
    }

    /**
     * Update the target's data to match the source.
     * Before calling this, make sure canMorph(target, source) is true.
     *
     * @param target The target path represented in an array of PathDataNode
     * @param source The source path represented in an array of PathDataNode
     */
    fun updateNodes(
        target: Array<PathDataNode>,
        source: Array<PathDataNode>
    ) {
        for (i in source.indices) {
            target[i].type = source[i].type
            for (j in source[i].params.indices) {
                target[i].params[j] = source[i].params[j]
            }
        }
    }

    private fun nextStart(s: String, end: Int): Int {
        var end = end
        var c: Char

        while (end < s.length) {
            c = s[end]
            // Note that 'e' or 'E' are not valid path commands, but could be
            // used for floating point numbers' scientific notation.
            // Therefore, when searching for next command, we should ignore 'e'
            // and 'E'.
            if ((((c.code - 'A'.code) * (c.code - 'Z'.code) <= 0) || ((c.code - 'a'.code) * (c.code - 'z'.code) <= 0))
                && c != 'e' && c != 'E'
            ) {
                return end
            }
            end++
        }
        return end
    }

    private fun addNode(list: ArrayList<PathDataNode>, cmd: Char, `val`: FloatArray) {
        list.add(PathDataNode(cmd, `val`))
    }

    /**
     * Parse the floats in the string.
     * This is an optimized version of parseFloat(s.split(",|\\s"));
     *
     * @param s the string containing a command and list of floats
     * @return array of floats
     */
    private fun getFloats(s: String): FloatArray {
        if (s[0] == 'z' || s[0] == 'Z') {
            return FloatArray(0)
        }
        try {
            val results = FloatArray(s.length)
            var count = 0
            var startPosition = 1
            var endPosition = 0

            val result = ExtractFloatResult()
            val totalLength = s.length

            // The startPosition should always be the first character of the
            // current number, and endPosition is the character after the current
            // number.
            while (startPosition < totalLength) {
                extract(s, startPosition, result)
                endPosition = result.mEndPosition

                if (startPosition < endPosition) {
                    results[count++] = s.substring(startPosition, endPosition).toFloat()
                }

                startPosition = if (result.mEndWithNegOrDot) {
                    // Keep the '-' or '.' sign with next number.
                    endPosition
                } else {
                    endPosition + 1
                }
            }
            return copyOfRange(results, 0, count)
        } catch (e: NumberFormatException) {
            throw RuntimeException("error in parsing \"$s\"", e)
        }
    }

    /**
     * Calculate the position of the next comma or space or negative sign
     *
     * @param s      the string to search
     * @param start  the position to start searching
     * @param result the result of the extraction, including the position of the
     * the starting position of next number, whether it is ending with a '-'.
     */
    private fun extract(s: String, start: Int, result: ExtractFloatResult) {
        // Now looking for ' ', ',', '.' or '-' from the start.
        var currentIndex = start
        var foundSeparator = false
        result.mEndWithNegOrDot = false
        var secondDot = false
        var isExponential = false
        while (currentIndex < s.length) {
            val isPrevExponential = isExponential
            isExponential = false
            val currentChar = s[currentIndex]
            when (currentChar) {
                ' ', ',' -> foundSeparator = true
                '-' ->                     // The negative sign following a 'e' or 'E' is not a separator.
                    if (currentIndex != start && !isPrevExponential) {
                        foundSeparator = true
                        result.mEndWithNegOrDot = true
                    }

                '.' -> if (!secondDot) {
                    secondDot = true
                } else {
                    // This is the second dot, and it is considered as a separator.
                    foundSeparator = true
                    result.mEndWithNegOrDot = true
                }

                'e', 'E' -> isExponential = true
            }
            if (foundSeparator) {
                break
            }
            currentIndex++
        }
        // When there is nothing found, then we put the end position to the end
        // of the string.
        result.mEndPosition = currentIndex
    }

    /**
     * Interpolate between two arrays of PathDataNodes with the given fraction, and store the
     * results in the first parameter.
     *
     * @param target   The resulting array of [PathDataNode] for the interpolation
     * @param fraction A float fraction value in the range of 0 to 1
     * @param from     The array of [PathDataNode] when fraction is 0
     * @param to       The array of [PathDataNode] when the fraction is 1
     * @throws IllegalArgumentException When the arrays of nodes are incompatible for interpolation.
     * @see .canMorph
     */
    fun interpolatePathDataNodes(
        target: Array<PathDataNode>,
        fraction: Float,
        from: Array<PathDataNode>,
        to: Array<PathDataNode>
    ) {
        require(
            interpolatePathDataNodes(
                target,
                from,
                to,
                fraction
            )
        ) { "Can't interpolate between two incompatible pathData" }
    }

    /**
     * Interpolate between two arrays of PathDataNodes with the given fraction, and store the
     * results in the first parameter.
     *
     * @param target   The resulting array of [PathDataNode] for the interpolation
     * @param from     The array of [PathDataNode] when fraction is 0
     * @param to       The array of [PathDataNode] when the fraction is 1
     * @param fraction A float fraction value in the range of 0 to 1
     * @throws IllegalArgumentException When the arrays of nodes are incompatible for interpolation.
     * @see .canMorph
     */
    @Deprecated(
        """Use
      {@link #interpolatePathDataNodes(PathDataNode[], float, PathDataNode[], PathDataNode[])}
      instead."""
    )
    fun interpolatePathDataNodes(
        target: Array<PathDataNode>,
        from: Array<PathDataNode>,
        to: Array<PathDataNode>,
        fraction: Float
    ): Boolean {
        require(!(target.size != from.size || from.size != to.size)) {
            ("The nodes to be interpolated and resulting nodes"
                    + " must have the same length")
        }

        if (!canMorph(from, to)) {
            return false
        }
        // Now do the interpolation
        for (i in target.indices) {
            target[i].interpolatePathDataNode(from[i], to[i], fraction)
        }
        return true
    }

    /**
     * Convert an array of PathDataNode to Path.
     *
     * @param node The source array of PathDataNode.
     * @param path The target Path object.
     */
    fun nodesToPath(
        node: Array<PathDataNode>,
        path: Path
    ) {
        val current = FloatArray(6)
        var previousCommand = 'm'
        for (pathDataNode in node) {
            PathDataNode.addCommand(
                path, current, previousCommand, pathDataNode.type,
                pathDataNode.params
            )
            previousCommand = pathDataNode.type
        }
    }

    private class ExtractFloatResult {
        // We need to return the position of the next separator and whether the
        // next float starts with a '-' or a '.'.
        var mEndPosition: Int = 0
        var mEndWithNegOrDot: Boolean = false
    }

    /**
     * Each PathDataNode represents one command in the "d" attribute of the svg
     * file.
     * An array of PathDataNode can represent the whole "d" attribute.
     */
    class PathDataNode {
        /**
         */
        var type: Char

        /**
         */
        val params: FloatArray


        internal constructor(type: Char, params: FloatArray) {
            this.type = type
            this.params = params
        }

        internal constructor(n: PathDataNode) {
            type = n.type
            params = copyOfRange(n.params, 0, n.params.size)
        }

        /**
         * The current PathDataNode will be interpolated between the
         * `nodeFrom` and `nodeTo` according to the
         * `fraction`.
         *
         * @param nodeFrom The start value as a PathDataNode.
         * @param nodeTo   The end value as a PathDataNode
         * @param fraction The fraction to interpolate.
         */
        fun interpolatePathDataNode(
            nodeFrom: PathDataNode,
            nodeTo: PathDataNode, fraction: Float
        ) {
            type = nodeFrom.type
            for (i in nodeFrom.params.indices) {
                params[i] = (nodeFrom.params[i] * (1 - fraction)
                        + nodeTo.params[i] * fraction)
            }
        }

        companion object {
            /**
             * Convert an array of PathDataNode to Path.
             *
             * @param node The source array of PathDataNode.
             * @param path The target Path object.
             */
            @Deprecated("Use {@link PathParser#nodesToPath(PathDataNode[], Path)} instead.")
            fun nodesToPath(
                node: Array<PathDataNode>,
                path: Path
            ) {
                PathParser.nodesToPath(node, path)
            }

            internal fun addCommand(
                path: Path, current: FloatArray,
                previousCmd: Char, cmd: Char, `val`: FloatArray
            ) {
                var previousCmd = previousCmd
                var incr = 2
                var currentX = current[0]
                var currentY = current[1]
                var ctrlPointX = current[2]
                var ctrlPointY = current[3]
                var currentSegmentStartX = current[4]
                var currentSegmentStartY = current[5]
                var reflectiveCtrlPointX: Float
                var reflectiveCtrlPointY: Float

                when (cmd) {
                    'z', 'Z' -> {
                        path.close()
                        // Path is closed here, but we need to move the pen to the
                        // closed position. So we cache the segment's starting position,
                        // and restore it here.
                        currentX = currentSegmentStartX
                        currentY = currentSegmentStartY
                        ctrlPointX = currentSegmentStartX
                        ctrlPointY = currentSegmentStartY
                        path.moveTo(currentX, currentY)
                    }

                    'm', 'M', 'l', 'L', 't', 'T' -> incr = 2
                    'h', 'H', 'v', 'V' -> incr = 1
                    'c', 'C' -> incr = 6
                    's', 'S', 'q', 'Q' -> incr = 4
                    'a', 'A' -> incr = 7
                }

                var k = 0
                while (k < `val`.size) {
                    when (cmd) {
                        'm' -> {
                            currentX += `val`[k + 0]
                            currentY += `val`[k + 1]
                            if (k > 0) {
                                // According to the spec, if a moveto is followed by multiple
                                // pairs of coordinates, the subsequent pairs are treated as
                                // implicit lineto commands.
                                path.lineTo(`val`[k + 0], `val`[k + 1])
                            } else {
                                path.moveTo(`val`[k + 0], `val`[k + 1])
                                currentSegmentStartX = currentX
                                currentSegmentStartY = currentY
                            }
                        }

                        'M' -> {
                            currentX = `val`[k + 0]
                            currentY = `val`[k + 1]
                            if (k > 0) {
                                // According to the spec, if a moveto is followed by multiple
                                // pairs of coordinates, the subsequent pairs are treated as
                                // implicit lineto commands.
                                path.lineTo(`val`[k + 0], `val`[k + 1])
                            } else {
                                path.moveTo(`val`[k + 0], `val`[k + 1])
                                currentSegmentStartX = currentX
                                currentSegmentStartY = currentY
                            }
                        }

                        'l' -> {
                            path.lineTo(`val`[k + 0], `val`[k + 1])
                            currentX += `val`[k + 0]
                            currentY += `val`[k + 1]
                        }

                        'L' -> {
                            path.lineTo(`val`[k + 0], `val`[k + 1])
                            currentX = `val`[k + 0]
                            currentY = `val`[k + 1]
                        }

                        'h' -> {
                            path.lineTo(`val`[k + 0], 0f)
                            currentX += `val`[k + 0]
                        }

                        'H' -> {
                            path.lineTo(`val`[k + 0], currentY)
                            currentX = `val`[k + 0]
                        }

                        'v' -> {
                            path.lineTo(0f, `val`[k + 0])
                            currentY += `val`[k + 0]
                        }

                        'V' -> {
                            path.lineTo(currentX, `val`[k + 0])
                            currentY = `val`[k + 0]
                        }

                        'c' -> {
                            path.cubicTo(
                                `val`[k + 0], `val`[k + 1], `val`[k + 2], `val`[k + 3],
                                `val`[k + 4], `val`[k + 5]
                            )

                            ctrlPointX = currentX + `val`[k + 2]
                            ctrlPointY = currentY + `val`[k + 3]
                            currentX += `val`[k + 4]
                            currentY += `val`[k + 5]
                        }

                        'C' -> {
                            path.cubicTo(
                                `val`[k + 0], `val`[k + 1], `val`[k + 2], `val`[k + 3],
                                `val`[k + 4], `val`[k + 5]
                            )
                            currentX = `val`[k + 4]
                            currentY = `val`[k + 5]
                            ctrlPointX = `val`[k + 2]
                            ctrlPointY = `val`[k + 3]
                        }

                        's' -> {
                            reflectiveCtrlPointX = 0f
                            reflectiveCtrlPointY = 0f
                            if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                                reflectiveCtrlPointX = currentX - ctrlPointX
                                reflectiveCtrlPointY = currentY - ctrlPointY
                            }
                            path.cubicTo(
                                reflectiveCtrlPointX, reflectiveCtrlPointY,
                                `val`[k + 0], `val`[k + 1],
                                `val`[k + 2], `val`[k + 3]
                            )

                            ctrlPointX = currentX + `val`[k + 0]
                            ctrlPointY = currentY + `val`[k + 1]
                            currentX += `val`[k + 2]
                            currentY += `val`[k + 3]
                        }

                        'S' -> {
                            reflectiveCtrlPointX = currentX
                            reflectiveCtrlPointY = currentY
                            if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                                reflectiveCtrlPointX = 2 * currentX - ctrlPointX
                                reflectiveCtrlPointY = 2 * currentY - ctrlPointY
                            }
                            path.cubicTo(
                                reflectiveCtrlPointX, reflectiveCtrlPointY,
                                `val`[k + 0], `val`[k + 1], `val`[k + 2], `val`[k + 3]
                            )
                            ctrlPointX = `val`[k + 0]
                            ctrlPointY = `val`[k + 1]
                            currentX = `val`[k + 2]
                            currentY = `val`[k + 3]
                        }

                        'q' -> {
                            path.quadraticBezierTo(`val`[k + 0], `val`[k + 1], `val`[k + 2], `val`[k + 3])
                            ctrlPointX = currentX + `val`[k + 0]
                            ctrlPointY = currentY + `val`[k + 1]
                            currentX += `val`[k + 2]
                            currentY += `val`[k + 3]
                        }

                        'Q' -> {
                            path.quadraticBezierTo(`val`[k + 0], `val`[k + 1], `val`[k + 2], `val`[k + 3])
                            ctrlPointX = `val`[k + 0]
                            ctrlPointY = `val`[k + 1]
                            currentX = `val`[k + 2]
                            currentY = `val`[k + 3]
                        }

                        't' -> {
                            reflectiveCtrlPointX = 0f
                            reflectiveCtrlPointY = 0f
                            if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                                reflectiveCtrlPointX = currentX - ctrlPointX
                                reflectiveCtrlPointY = currentY - ctrlPointY
                            }
                            path.quadraticBezierTo(
                                reflectiveCtrlPointX, reflectiveCtrlPointY,
                                `val`[k + 0], `val`[k + 1]
                            )
                            ctrlPointX = currentX + reflectiveCtrlPointX
                            ctrlPointY = currentY + reflectiveCtrlPointY
                            currentX += `val`[k + 0]
                            currentY += `val`[k + 1]
                        }

                        'T' -> {
                            reflectiveCtrlPointX = currentX
                            reflectiveCtrlPointY = currentY
                            if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                                reflectiveCtrlPointX = 2 * currentX - ctrlPointX
                                reflectiveCtrlPointY = 2 * currentY - ctrlPointY
                            }
                            path.quadraticBezierTo(
                                reflectiveCtrlPointX, reflectiveCtrlPointY,
                                `val`[k + 0], `val`[k + 1]
                            )
                            ctrlPointX = reflectiveCtrlPointX
                            ctrlPointY = reflectiveCtrlPointY
                            currentX = `val`[k + 0]
                            currentY = `val`[k + 1]
                        }

                        'a' -> {
                            // (rx ry x-axis-rotation large-arc-flag sweep-flag x y)
                            drawArc(
                                path,
                                currentX,
                                currentY,
                                `val`[k + 5] + currentX,
                                `val`[k + 6] + currentY,
                                `val`[k + 0],
                                `val`[k + 1],
                                `val`[k + 2],
                                `val`[k + 3] != 0f,
                                `val`[k + 4] != 0f
                            )
                            currentX += `val`[k + 5]
                            currentY += `val`[k + 6]
                            ctrlPointX = currentX
                            ctrlPointY = currentY
                        }

                        'A' -> {
                            drawArc(
                                path,
                                currentX,
                                currentY,
                                `val`[k + 5],
                                `val`[k + 6],
                                `val`[k + 0],
                                `val`[k + 1],
                                `val`[k + 2],
                                `val`[k + 3] != 0f,
                                `val`[k + 4] != 0f
                            )
                            currentX = `val`[k + 5]
                            currentY = `val`[k + 6]
                            ctrlPointX = currentX
                            ctrlPointY = currentY
                        }
                    }
                    previousCmd = cmd
                    k += incr
                }
                current[0] = currentX
                current[1] = currentY
                current[2] = ctrlPointX
                current[3] = ctrlPointY
                current[4] = currentSegmentStartX
                current[5] = currentSegmentStartY
            }

            private fun drawArc(
                p: Path,
                x0: Float,
                y0: Float,
                x1: Float,
                y1: Float,
                a: Float,
                b: Float,
                theta: Float,
                isMoreThanHalf: Boolean,
                isPositiveArc: Boolean
            ) {
                /* Convert rotation angle from degrees to radians */

                val thetaD = toRadians(theta.toDouble())
                /* Pre-compute rotation matrix entries */
                val cosTheta = cos(thetaD)
                val sinTheta = sin(thetaD)
                /* Transform (x0, y0) and (x1, y1) into unit space */
                /* using (inverse) rotation, followed by (inverse) scale */
                val x0p = (x0 * cosTheta + y0 * sinTheta) / a
                val y0p = (-x0 * sinTheta + y0 * cosTheta) / b
                val x1p = (x1 * cosTheta + y1 * sinTheta) / a
                val y1p = (-x1 * sinTheta + y1 * cosTheta) / b

                /* Compute differences and averages */
                val dx = x0p - x1p
                val dy = y0p - y1p
                val xm = (x0p + x1p) / 2
                val ym = (y0p + y1p) / 2
                /* Solve for intersecting unit circles */
                val dsq = dx * dx + dy * dy
                if (dsq == 0.0) {
                    //Log.w(LOGTAG, " Points are coincident");
                    return  /* Points are coincident */
                }
                val disc = 1.0 / dsq - 1.0 / 4.0
                if (disc < 0.0) {
                    //Log.w(LOGTAG, "Points are too far apart " + dsq);
                    val adjust = (sqrt(dsq) / 1.99999).toFloat()
                    drawArc(
                        p, x0, y0, x1, y1, a * adjust,
                        b * adjust, theta, isMoreThanHalf, isPositiveArc
                    )
                    return  /* Points are too far apart */
                }
                val s = sqrt(disc)
                val sdx = s * dx
                val sdy = s * dy
                var cx: Double
                var cy: Double
                if (isMoreThanHalf == isPositiveArc) {
                    cx = xm - sdy
                    cy = ym + sdx
                } else {
                    cx = xm + sdy
                    cy = ym - sdx
                }

                val eta0 = atan2((y0p - cy), (x0p - cx))

                val eta1 = atan2((y1p - cy), (x1p - cx))

                var sweep = (eta1 - eta0)
                if (isPositiveArc != (sweep >= 0)) {
                    if (sweep > 0) {
                        sweep -= 2 * PI
                    } else {
                        sweep += 2 * PI
                    }
                }

                cx *= a.toDouble()
                cy *= b.toDouble()
                val tcx = cx
                cx = cx * cosTheta - cy * sinTheta
                cy = tcx * sinTheta + cy * cosTheta

                arcToBezier(p, cx, cy, a.toDouble(), b.toDouble(), x0.toDouble(), y0.toDouble(), thetaD, eta0, sweep)
            }

            /**
             * Converts an arc to cubic Bezier segments and records them in p.
             *
             * @param p     The target for the cubic Bezier segments
             * @param cx    The x coordinate center of the ellipse
             * @param cy    The y coordinate center of the ellipse
             * @param a     The radius of the ellipse in the horizontal direction
             * @param b     The radius of the ellipse in the vertical direction
             * @param e1x   E(eta1) x coordinate of the starting point of the arc
             * @param e1y   E(eta2) y coordinate of the starting point of the arc
             * @param theta The angle that the ellipse bounding rectangle makes with horizontal plane
             * @param start The start angle of the arc on the ellipse
             * @param sweep The angle (positive or negative) of the sweep of the arc on the ellipse
             */
            private fun arcToBezier(
                p: Path,
                cx: Double,
                cy: Double,
                a: Double,
                b: Double,
                e1x: Double,
                e1y: Double,
                theta: Double,
                start: Double,
                sweep: Double
            ) {
                // Taken from equations at: http://spaceroots.org/documents/ellipse/node8.html
                // and http://www.spaceroots.org/documents/ellipse/node22.html

                // Maximum of 45 degrees per cubic Bezier segment

                var e1x = e1x
                var e1y = e1y
                val numSegments = ceil(abs(sweep * 4 / PI)).toInt()

                var eta1 = start
                val cosTheta = cos(theta)
                val sinTheta = sin(theta)
                val cosEta1 = cos(eta1)
                val sinEta1 = sin(eta1)
                var ep1x = (-a * cosTheta * sinEta1) - (b * sinTheta * cosEta1)
                var ep1y = (-a * sinTheta * sinEta1) + (b * cosTheta * cosEta1)

                val anglePerSegment = sweep / numSegments
                for (i in 0..<numSegments) {
                    val eta2 = eta1 + anglePerSegment
                    val sinEta2 = sin(eta2)
                    val cosEta2 = cos(eta2)
                    val e2x = cx + (a * cosTheta * cosEta2) - (b * sinTheta * sinEta2)
                    val e2y = cy + (a * sinTheta * cosEta2) + (b * cosTheta * sinEta2)
                    val ep2x = -a * cosTheta * sinEta2 - b * sinTheta * cosEta2
                    val ep2y = -a * sinTheta * sinEta2 + b * cosTheta * cosEta2
                    val tanDiff2 = tan((eta2 - eta1) / 2)
                    val alpha =
                        sin(eta2 - eta1) * (sqrt(4 + (3 * tanDiff2 * tanDiff2)) - 1) / 3
                    val q1x = e1x + alpha * ep1x
                    val q1y = e1y + alpha * ep1y
                    val q2x = e2x - alpha * ep2x
                    val q2y = e2y - alpha * ep2y

                    // Adding this no-op call to workaround a proguard related issue.
                    p.lineTo(0f, 0f)

                    p.cubicTo(
                        q1x.toFloat(),
                        q1y.toFloat(),
                        q2x.toFloat(),
                        q2y.toFloat(),
                        e2x.toFloat(),
                        e2y.toFloat()
                    )
                    eta1 = eta2
                    e1x = e2x
                    e1y = e2y
                    ep1x = ep2x
                    ep1y = ep2y
                }
            }
        }
    }

    fun toRadians(deg: Double): Double = deg / 180.0 * PI

    fun toDegrees(rad: Double): Double = rad * 180.0 / PI
}
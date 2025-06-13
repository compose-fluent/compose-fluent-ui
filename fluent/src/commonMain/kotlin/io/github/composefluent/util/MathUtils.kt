package io.github.composefluent.util

import kotlin.math.PI
import kotlin.math.round


internal object MathUtils {
    fun degreesToRadians(degrees: Double): Double {
        return degrees * (PI / 180.0)
    }

    fun clampToUnit(c: Double): Double {
        if (c.isNaN()) {
            return 0.0
        } else if (c == Double.POSITIVE_INFINITY) {
            return 1.0
        } else if (c == Double.NEGATIVE_INFINITY) {
            return 0.0
        }
        if (c <= 0) {
            return 0.0
        } else if (c >= 1) {
            return 1.0
        } else {
            return c;
        }
    }

    fun radiansToDegrees(radians: Double): Double {
        return radians * (180.0 / PI)
    }

    fun lerp(left: Double, right: Double, scale: Double): Double {
        if (scale <= 0) {
            return left
        } else if (scale >= 1) {
            return right
        }
        return left + scale * (right - left)
    }

    fun lerp(left: Int, right: Int, scale: Double): Int {
        if (scale <= 0) {
            return left
        } else if (scale >= 1) {
            return right
        }
        val l = left.toDouble()
        val r = right.toDouble()
        return round(l + scale * (r - l)).toInt()
    }

    fun clampToByte(c: Double): Int {
        if (c.isNaN()) {
            return 0
        } else if (c == Double.POSITIVE_INFINITY) {
            return 255;
        } else if (c == Double.NEGATIVE_INFINITY) {
            return 0;
        }
        val c = round(c)
        return if (c <= 0) {
            0;
        } else if (c >= 255) {
            255;
        } else {
            c.toInt()
        }
    }
}
package io.github.composefluent.component

import java.text.NumberFormat
import java.text.ParsePosition
import java.util.Locale

internal actual fun createPlatformNumberBoxFormatter(): NumberBoxFormatter = AndroidNumberBoxFormatter()

private class AndroidNumberBoxFormatter : NumberBoxFormatter {
    private val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 10
        minimumFractionDigits = 0
    }

    override fun format(value: Double): String = numberFormat.format(value)

    override fun parse(text: String): Double? {
        val input = text.trim()
        if (input.isEmpty()) {
            return null
        }
        val position = ParsePosition(0)
        val number = numberFormat.parse(input, position) ?: return null
        return number.toDouble().takeIf {
            position.index == input.length && position.errorIndex < 0 && it.isFinite()
        }
    }
}

package io.github.composefluent.component

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

internal actual fun createPlatformNumberBoxFormatter(): NumberBoxFormatter = AppleNumberBoxFormatter()

private class AppleNumberBoxFormatter : NumberBoxFormatter {
    private val numberFormatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        minimumFractionDigits = 0u
        maximumFractionDigits = 10u
        lenient = false
    }

    override fun format(value: Double): String =
        numberFormatter.stringFromNumber(NSNumber(value)) ?: value.toString()

    override fun parse(text: String): Double? {
        val input = text.trim()
        if (input.isEmpty()) {
            return null
        }
        return numberFormatter.numberFromString(input)?.doubleValue?.takeIf { it.isFinite() }
    }
}

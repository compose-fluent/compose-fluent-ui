package io.github.composefluent.component

internal actual fun createPlatformNumberBoxFormatter(): NumberBoxFormatter = JsNumberBoxFormatter()

private class JsNumberBoxFormatter : NumberBoxFormatter {
    override fun format(value: Double): String = js(
        "new Intl.NumberFormat(undefined, { maximumFractionDigits: 10 }).format(value)"
    ) as String

    override fun parse(text: String): Double? {
        val input = text.trim()
        if (input.isEmpty()) {
            return null
        }
        return js(
            """
                (() => {
                    const parts = new Intl.NumberFormat(undefined, {
                        maximumFractionDigits: 10
                    }).formatToParts(12345.6);
                    const groupPart = parts.find(part => part.type === 'group');
                    const decimalPart = parts.find(part => part.type === 'decimal');
                    const group = groupPart ? groupPart.value : '';
                    const decimal = decimalPart ? decimalPart.value : '.';
                    const signParts = new Intl.NumberFormat(undefined, {
                        signDisplay: 'always'
                    }).formatToParts(-1);
                    const minusPart = signParts.find(part => part.type === 'minusSign');
                    const plusPart = new Intl.NumberFormat(undefined, {
                        signDisplay: 'always'
                    }).formatToParts(1).find(part => part.type === 'plusSign');
                    const digits = new Intl.NumberFormat(undefined, {
                        useGrouping: false,
                        maximumFractionDigits: 0
                    });
                    let normalized = input;
                    if (group) normalized = normalized.split(group).join('');
                    if (decimal !== '.') normalized = normalized.split(decimal).join('.');
                    if (minusPart) normalized = normalized.split(minusPart.value).join('-');
                    if (plusPart) normalized = normalized.split(plusPart.value).join('+');
                    for (let digit = 0; digit <= 9; digit++) {
                        normalized = normalized.split(digits.format(digit)).join(String(digit));
                    }
                    normalized = normalized.replace(/[\u061C\u200E\u200F]/g, '');
                    if (!/^[+-]?(?:\d+(?:\.\d*)?|\.\d+)$/.test(normalized)) return null;
                    const value = Number(normalized);
                    return Number.isFinite(value) ? value : null;
                })()
            """
        ) as Double?
    }
}

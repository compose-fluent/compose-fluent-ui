package io.github.composefluent.gallery.code

/** Syntax categories, independent of WinUI and of the selected editor palette. */
enum class KotlinCodeKind {
    Plain, Keyword, String, Escape, Number, Comment, Documentation, Annotation,
    Function, FunctionCall, StaticFunctionCall, Constructor, Property, StaticProperty,
    Variable, Parameter, Type, TypeParameter, NamedArgument, Label,
}

data class KotlinCodeSpan(val end: Int, val kind: KotlinCodeKind)

/** UTF-16 offsets preserve the original text, including whitespace and surrogate pairs. */
data class KotlinCodeDocument(val fileName: String, val source: String, val spans: List<KotlinCodeSpan>) {
    init {
        var start = 0
        spans.forEach { span ->
            require(span.end > start && span.end <= source.length)
            start = span.end
        }
        require(start == source.length)
    }

    companion object {
        val Empty = KotlinCodeDocument("", "", emptyList())
    }
}

data class KotlinCodeStyle(val foreground: UInt, val italic: Boolean = false)

/**
 * IDEA 2026.2 (idea/262.8665.337): expUI_lightScheme / IslandSchemeDark and
 * KotlinHighlightingColors' fallback keys. Unstyled names inherit TEXT.
 */
data class KotlinCodePalette(val background: UInt, val styles: Map<KotlinCodeKind, KotlinCodeStyle>) {
    fun style(kind: KotlinCodeKind) = styles[kind] ?: styles.getValue(KotlinCodeKind.Plain)

    companion object {
        val Light = KotlinCodePalette(0xFFFFFFu, mapOf(
            KotlinCodeKind.Plain to KotlinCodeStyle(0x080808u),
            KotlinCodeKind.Keyword to KotlinCodeStyle(0x0033B3u),
            KotlinCodeKind.String to KotlinCodeStyle(0x067D17u),
            KotlinCodeKind.Escape to KotlinCodeStyle(0x0037A6u),
            KotlinCodeKind.Number to KotlinCodeStyle(0x1750EBu),
            KotlinCodeKind.Comment to KotlinCodeStyle(0x8C8C8Cu, true),
            KotlinCodeKind.Documentation to KotlinCodeStyle(0x8C8C8Cu, true),
            KotlinCodeKind.Annotation to KotlinCodeStyle(0x9E880Du),
            KotlinCodeKind.Function to KotlinCodeStyle(0x00627Au),
            KotlinCodeKind.Property to KotlinCodeStyle(0x871094u),
            KotlinCodeKind.StaticProperty to KotlinCodeStyle(0x871094u, true),
            KotlinCodeKind.StaticFunctionCall to KotlinCodeStyle(0x00627Au, true),
            KotlinCodeKind.TypeParameter to KotlinCodeStyle(0x007E8Au),
        ))
        val Dark = KotlinCodePalette(0x1E1F22u, mapOf(
            KotlinCodeKind.Plain to KotlinCodeStyle(0xBCBEC4u),
            KotlinCodeKind.Keyword to KotlinCodeStyle(0xCF8E6Du),
            KotlinCodeKind.String to KotlinCodeStyle(0x6AAB73u),
            KotlinCodeKind.Escape to KotlinCodeStyle(0xCF8E6Du),
            KotlinCodeKind.Number to KotlinCodeStyle(0x2AACB8u),
            KotlinCodeKind.Comment to KotlinCodeStyle(0x7A7E85u),
            KotlinCodeKind.Documentation to KotlinCodeStyle(0x5F826Bu, true),
            KotlinCodeKind.Annotation to KotlinCodeStyle(0xB3AE60u),
            KotlinCodeKind.Function to KotlinCodeStyle(0x56A8F5u),
            KotlinCodeKind.Property to KotlinCodeStyle(0xC77DBBu),
            KotlinCodeKind.StaticProperty to KotlinCodeStyle(0xC77DBBu, true),
            KotlinCodeKind.StaticFunctionCall to KotlinCodeStyle(0x57AAF7u, true),
            KotlinCodeKind.TypeParameter to KotlinCodeStyle(0x16BAACu),
            KotlinCodeKind.NamedArgument to KotlinCodeStyle(0x56C1D6u),
            KotlinCodeKind.Label to KotlinCodeStyle(0x32B8AFu),
        ))
    }
}

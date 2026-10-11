package io.github.composefluent.gallery.processor

import io.github.composefluent.gallery.code.KotlinCodeDocument
import io.github.composefluent.gallery.code.KotlinCodeKind
import io.github.composefluent.gallery.code.KotlinCodeSpan
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class KotlinSourceParserTest {
    @Test fun astDistinguishesDeclarationsCallsNamedArgumentsAndTypes() {
        val source = """@Sample
fun <T> render(input: Widget) {
    val count = 42
    Button(onClick = { show(input) })
}
"""
        val document = KotlinSourceParser().parse("Sample.kt", source)
        fun kindAt(value: String) = document.spans.first { it.end > source.indexOf(value) }.kind
        assertEquals(KotlinCodeKind.Annotation, kindAt("Sample"))
        assertEquals(KotlinCodeKind.Function, kindAt("render"))
        assertEquals(KotlinCodeKind.TypeParameter, kindAt("T>"))
        assertEquals(KotlinCodeKind.Parameter, kindAt("input:"))
        assertEquals(KotlinCodeKind.Type, kindAt("Widget"))
        assertEquals(KotlinCodeKind.Variable, kindAt("count"))
        assertEquals(KotlinCodeKind.Number, kindAt("42"))
        assertEquals(KotlinCodeKind.FunctionCall, kindAt("show"))
        assertEquals(KotlinCodeKind.NamedArgument, kindAt("onClick"))
    }

    @Test fun commentsStringsTemplatesAndUnicodeKeepExactUtf16Ranges() {
        val source = "/** Doc */\n/* outer /* nested */ end */\nval text = \"你好 😀 \\n \${count + 1}\"\n"
        val document = KotlinSourceParser().parse("Sample.kts", source)
        var start = 0
        val pieces = document.spans.map { span ->
            (source.substring(start, span.end) to span.kind).also { start = span.end }
        }
        assertEquals(source, pieces.joinToString("") { it.first })
        assertTrue(pieces.any { it.second == KotlinCodeKind.Documentation })
        assertTrue(pieces.any { "nested" in it.first && it.second == KotlinCodeKind.Comment })
        assertTrue(pieces.any { "你好 😀" in it.first && it.second == KotlinCodeKind.String })
        assertTrue(pieces.any { it.first == "\\n" && it.second == KotlinCodeKind.Escape })
        assertEquals(document, KotlinSourceParser().parse("Sample.kts", source))
    }

    @Test fun emptyRawAndIncompleteSamplesDoNotLoseText() {
        for (source in listOf("", "val text = \"\"\"if (true) // literal\n\${1 + 2}\"\"\"", "fun unfinished(\n")) {
            val document = KotlinSourceParser().parse("Sample.kts", source)
            assertEquals(source, document.source)
            assertEquals(source.length, document.spans.lastOrNull()?.end ?: 0)
        }
        assertFailsWith<IllegalArgumentException> {
            KotlinCodeDocument("Bad.kt", "text", listOf(KotlinCodeSpan(3, KotlinCodeKind.Plain)))
        }
    }

    @Test fun generatedKotlinChunksLargeSamplesAndEscapesTemplatesAndSurrogates() {
        val source = ("\$name 😀\n").repeat(1500)
        val document = KotlinCodeDocument("Large.kt", source, listOf(KotlinCodeSpan(source.length, KotlinCodeKind.String)))
        val generated = generateSampleDocuments("sample", listOf("sourceCodeOfLarge" to document))
        assertTrue(generated.contains("\\\$name"))
        assertTrue(generated.contains("\\ud83d\\ude00"))
        assertTrue(generated.contains("joinToString"))
        assertTrue(generated.contains("KotlinCodeDocument by lazy"))
        assertEquals(generated, generateSampleDocuments("sample", listOf("sourceCodeOfLarge" to document)))
    }
}

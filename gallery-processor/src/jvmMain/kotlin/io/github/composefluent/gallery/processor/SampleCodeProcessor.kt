package io.github.composefluent.gallery.processor

import com.google.devtools.ksp.impl.symbol.kotlin.KSFunctionDeclarationImpl
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.composefluent.gallery.code.KotlinCodeDocument
import ksp.org.jetbrains.kotlin.psi.KtDeclarationWithBody

/** KSP extracts samples; K2 LightTree owns syntax parsing at build time. */
class SampleCodeProcessor(private val logger: KSPLogger, private val codeGenerator: CodeGenerator) : IProcessor {
    private val functions = linkedMapOf<String, KSFunctionDeclaration>()

    override fun onFunctionVisit(function: KSFunctionDeclaration) {
        if (function.annotations.any { it.isTargetAnnotation("Sample") }) {
            functions[function.qualifiedName?.asString() ?: function.simpleName.asString()] = function
        }
    }

    override fun finish() {
        val parser = KotlinSourceParser()
        val registry = mutableListOf<Pair<String, String>>()
        functions.values.groupBy { it.packageName.asString() }.toSortedMap().forEach { (packageName, samples) ->
            val documents = samples.sortedBy { it.simpleName.asString() }.mapNotNull { function ->
                val psi = (function as? KSFunctionDeclarationImpl)?.ktDeclarationSymbol?.psi as? KtDeclarationWithBody
                val body = psi?.bodyExpression
                if (body == null) {
                    logger.error("@Sample must have a Kotlin function body", function)
                    return@mapNotNull null
                }
                val text = if (psi.bodyBlockExpression != null) {
                    body.text.removePrefix("{").removeSuffix("}").trimIndent()
                } else {
                    body.text.trimIndent()
                }
                val name = function.simpleName.asString().replaceFirstChar { it.uppercase() }
                registry += function.qualifiedName!!.asString() to "$packageName.sourceCodeOf$name"
                "sourceCodeOf$name" to parser.parse(function.containingFile!!.fileName, text, isScript = true)
            }
            if (documents.isNotEmpty()) {
                val dependencies = samples.mapNotNull { it.containingFile }.distinct().toTypedArray()
                codeGenerator.createNewFile(Dependencies(true, *dependencies), packageName, "_SampleCodeDocument")
                    .bufferedWriter(Charsets.UTF_8).use { it.write(generateSampleDocuments(packageName, documents)) }
            }
        }
        val dependencies = functions.values.mapNotNull { it.containingFile }.distinct().toTypedArray()
        codeGenerator.createNewFile(Dependencies(true, *dependencies), "io.github.composefluent.gallery.code", "SampleCodeDocuments")
            .bufferedWriter(Charsets.UTF_8).use { writer ->
                writer.appendLine("// Generated from @Sample function ASTs. Do not edit.")
                writer.appendLine("package io.github.composefluent.gallery.code")
                writer.appendLine("val sampleCodeDocuments: Map<String, KotlinCodeDocument> by lazy { mapOf(")
                registry.sortedBy { it.first }.forEach { (name, reference) ->
                    writer.appendLine("    ${kotlinLiteral(name)} to $reference,")
                }
                writer.appendLine(") }")
            }
    }
}

internal fun kotlinLiteral(value: String): String = buildString {
    append('"')
    value.forEach { append(when (it) {
        '\\' -> "\\\\"; '"' -> "\\\""; '$' -> "\\$"; '\n' -> "\\n"; '\r' -> "\\r"; '\t' -> "\\t"
        else -> if (it.code < 32 || it.code in 0xD800..0xDFFF) "\\u" + it.code.toString(16).padStart(4, '0') else it.toString()
    }) }
    append('"')
}

internal fun generateSampleDocuments(packageName: String, documents: List<Pair<String, KotlinCodeDocument>>): String = buildString {
    appendLine("// Generated from @Sample function ASTs. Do not edit.")
    appendLine("package $packageName")
    appendLine("import io.github.composefluent.gallery.code.*")
    documents.forEach { (name, document) ->
        appendLine("internal val $name: KotlinCodeDocument by lazy { ${name}Document.create() }")
        appendLine("private object ${name}Document {")
        appendLine("    fun create() = KotlinCodeDocument(${kotlinLiteral(document.fileName)}, source(), spans())")
        appendLine("    private fun source() = listOf<String>(")
        document.source.chunked(2000).forEach { appendLine("        ${kotlinLiteral(it)},") }
        appendLine("    ).joinToString(\"\")")
        val chunks = document.spans.chunked(150)
        appendLine("    private fun spans() = buildList<KotlinCodeSpan> {")
        chunks.indices.forEach { appendLine("        addAll(part$it())") }
        appendLine("    }")
        chunks.forEachIndexed { index, spans ->
            appendLine("    private fun part$index() = listOf(")
            spans.forEach { appendLine("        KotlinCodeSpan(${it.end}, KotlinCodeKind.${it.kind}),") }
            appendLine("    )")
        }
        appendLine("}")
    }
}

package io.github.composefluent.gallery.processor

import com.google.devtools.ksp.impl.symbol.kotlin.KSFunctionDeclarationImpl
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import ksp.org.jetbrains.kotlin.psi.KtDeclarationWithBody
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class SampleCodeProcessor(private val logger: KSPLogger, private val codeGenerator: CodeGenerator) : IProcessor {

    private val sampleAnnotation = "Sample"

    private val sampleCodeFunctions = mutableMapOf<String, MutableList<KSFunctionDeclaration>>()

    override fun onFunctionVisit(function: KSFunctionDeclaration) {
        super.onFunctionVisit(function)
        function.annotations.forEach {
            if (it.isTargetAnnotation(sampleAnnotation)) {
                sampleCodeFunctions
                    .getOrPut(function.packageName.asString()) { mutableListOf() }
                    .add(function)
            }
            return
        }
    }

    override fun finish() {
        super.finish()
        generateSampleCode()
    }

    private fun generateSampleCode() {
        val fileName = "_SampleCodeString"
        sampleCodeFunctions.forEach { (packageName, functions) ->
            if (functions.isNotEmpty()) {
                val sourceFile = FileSpec.builder(packageName, fileName)
                val sourceFileList = mutableListOf<KSFile>()
                functions.forEach { func ->
                    func.containingFile?.let { sourceFileList.add(it) }
                    if (func is KSFunctionDeclarationImpl) {
                        val bodyText = when (val psi = func.ktDeclarationSymbol.psi) {
                            is KtDeclarationWithBody -> {
                                psi.bodyBlockExpression?.text
                                    ?.removePrefix("{")
                                    ?.removeSuffix("}")
                                    ?.trimIndent() ?: psi.bodyBlockExpression
                                    ?.statements
                                    ?.joinToString(System.lineSeparator()) { statement -> statement.text }
                                    ?.trimIndent() ?: psi.text
                            }
                            else -> psi?.text ?: ""
                        }
                        val funcName = func.simpleName.asString()
                        sourceFile.addProperty(
                            PropertySpec.builder(
                                "sourceCodeOf${funcName.first().uppercase()}${funcName.substring(1)}",
                                String::class
                            )
                                .addModifiers(KModifier.INTERNAL)
                                .getter(
                                    FunSpec.getterBuilder()
                                        .addStatement("return %S", bodyText)
                                        .build()
                                )
                                .build()
                        )
                    }
                }
                val file = codeGenerator.createNewFile(
                    Dependencies(true, *(sourceFileList).toTypedArray()),
                    packageName,
                    fileName
                )
                OutputStreamWriter(file, StandardCharsets.UTF_8).use(sourceFile.build()::writeTo)

            }
        }
    }

}


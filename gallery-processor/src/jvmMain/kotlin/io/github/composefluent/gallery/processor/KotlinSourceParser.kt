package io.github.composefluent.gallery.processor

import io.github.composefluent.gallery.code.*
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderFactoryImpl
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.lexer.KotlinLexer
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.parsing.KotlinLightParser
import org.jetbrains.kotlin.parsing.KotlinParserDefinition
import org.jetbrains.kotlin.kdoc.lexer.KDocTokens

/** The LightTree parser used by K2/FIR, with no IDE project or compiler analysis session. */
internal class KotlinSourceParser {
    fun parse(fileName: String, source: String, isScript: Boolean = fileName.endsWith(".kts")): KotlinCodeDocument {
        val builder = PsiBuilderFactoryImpl().createBuilder(KotlinParserDefinition(), KotlinLexer(), source)
        val tree = KotlinLightParser.parse(builder, isScript = isScript)
        val syntax = mutableMapOf<Int, KotlinCodeKind>()
        fun visit(
            node: LighterASTNode,
            parent: IElementType? = null,
            grandparent: IElementType? = null,
            annotation: Boolean = false,
            typeReference: Boolean = false,
            importOrPackage: Boolean = false,
        ) {
            val type = node.tokenType
            val inAnnotation = (annotation || type == KtNodeTypes.ANNOTATION_ENTRY) && type != KtNodeTypes.VALUE_ARGUMENT_LIST
            val inTypeReference = typeReference || type == KtNodeTypes.TYPE_REFERENCE
            val inImportOrPackage = importOrPackage || type == KtNodeTypes.IMPORT_DIRECTIVE || type == KtNodeTypes.PACKAGE_DIRECTIVE
            if (KtTokens.SOFT_KEYWORDS.contains(type)) syntax[node.startOffset] = KotlinCodeKind.Keyword
            if (type == KtTokens.IDENTIFIER) {
                when {
                    inAnnotation -> KotlinCodeKind.Annotation
                    parent == KtNodeTypes.TYPE_PARAMETER -> KotlinCodeKind.TypeParameter
                    inTypeReference -> KotlinCodeKind.Type
                    parent == KtNodeTypes.FUN -> KotlinCodeKind.Function
                    parent == KtNodeTypes.PROPERTY -> if (grandparent == KtNodeTypes.BLOCK) KotlinCodeKind.Variable else KotlinCodeKind.Property
                    parent == KtNodeTypes.VALUE_PARAMETER -> KotlinCodeKind.Parameter
                    parent == KtNodeTypes.VALUE_ARGUMENT_NAME || grandparent == KtNodeTypes.VALUE_ARGUMENT_NAME -> KotlinCodeKind.NamedArgument
                    parent == KtNodeTypes.LABEL || parent == KtNodeTypes.LABEL_QUALIFIER -> KotlinCodeKind.Label
                    parent == KtNodeTypes.REFERENCE_EXPRESSION && grandparent == KtNodeTypes.CALL_EXPRESSION -> KotlinCodeKind.FunctionCall
                    parent == KtNodeTypes.OPERATION_REFERENCE && grandparent == KtNodeTypes.BINARY_EXPRESSION -> KotlinCodeKind.FunctionCall
                    parent == KtNodeTypes.REFERENCE_EXPRESSION && !inTypeReference && !inImportOrPackage -> KotlinCodeKind.Variable
                    else -> null
                }?.let { syntax[node.startOffset] = it }
            }
            val ref = Ref<Array<LighterASTNode?>>()
            val count = tree.getChildren(node, ref)
            val children = ref.get()
            if (children != null) {
                for (i in 0 until count) children[i]?.let {
                    visit(it, type, parent, inAnnotation, inTypeReference, inImportOrPackage)
                }
                tree.disposeChildren(children, count)
            }
        }
        visit(tree.root)
        val lexer = KotlinLexer().apply { start(source) }
        val spans = mutableListOf<KotlinCodeSpan>()
        var previousIdentifier = false
        while (lexer.tokenType != null) {
            val token = lexer.tokenType
            val kind = when {
                token == KDocTokens.KDOC -> KotlinCodeKind.Documentation
                KtTokens.COMMENTS.contains(token) -> KotlinCodeKind.Comment
                token == KtTokens.ESCAPE_SEQUENCE || token == KtTokens.SHORT_TEMPLATE_ENTRY_START ||
                    token == KtTokens.LONG_TEMPLATE_ENTRY_START || token == KtTokens.LONG_TEMPLATE_ENTRY_END -> KotlinCodeKind.Escape
                token == KtTokens.REGULAR_STRING_PART || token == KtTokens.OPEN_QUOTE ||
                    token == KtTokens.CLOSING_QUOTE || token == KtTokens.CHARACTER_LITERAL -> KotlinCodeKind.String
                token == KtTokens.INTEGER_LITERAL || token == KtTokens.FLOAT_LITERAL -> KotlinCodeKind.Number
                KtTokens.KEYWORDS.contains(token) -> KotlinCodeKind.Keyword
                token == KtTokens.AT -> KotlinCodeKind.Annotation
                token == KtTokens.IDENTIFIER -> syntax[lexer.tokenStart] ?: KotlinCodeKind.Plain
                else -> KotlinCodeKind.Plain
            }
            // Preserve identifier boundaries in the generated AST document.
            val identifier = token == KtTokens.IDENTIFIER
            if (!identifier && !previousIdentifier && spans.lastOrNull()?.kind == kind) {
                spans[spans.lastIndex] = KotlinCodeSpan(lexer.tokenEnd, kind)
            } else spans += KotlinCodeSpan(lexer.tokenEnd, kind)
            previousIdentifier = identifier
            lexer.advance()
        }
        return KotlinCodeDocument(fileName, source, spans)
    }

}

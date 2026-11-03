package io.github.composefluent.math

import kotlin.math.pow

internal object NumberBoxParser {
    fun compute(
        expression: String,
        parseNumber: (String) -> Double?,
    ): Double? {
        val tokens = tokenize(expression, parseNumber) ?: return null
        val postfix = toPostfix(tokens) ?: return null
        return evaluate(postfix)?.takeIf { it.isFinite() }
    }
}

private sealed interface MathToken {
    data class Number(val value: Double) : MathToken
    data class Operator(val symbol: Char) : MathToken
    data class Parenthesis(val symbol: Char) : MathToken
}

private fun tokenize(
    expression: String,
    parseNumber: (String) -> Double?,
): List<MathToken>? {
    val tokens = mutableListOf<MathToken>()
    var index = 0
    var expectsOperand = true

    while (index < expression.length) {
        if (expression[index].isWhitespace()) {
            index++
            continue
        }

        val character = expression[index]
        if (expectsOperand) {
            when {
                character == '(' -> {
                    tokens += MathToken.Parenthesis(character)
                    index++
                }

                character == '+' || character in BINARY_OPERATORS && character != '-' -> return null

                character == '-' && (
                    index + 1 >= expression.length ||
                        expression[index + 1].isWhitespace() ||
                        expression[index + 1] == '(' ||
                        expression[index + 1] in BINARY_OPERATORS
                    ) -> return null

                character == ')' -> return null

                else -> {
                    val start = index
                    if (character == '-') {
                        index++
                    }
                    while (
                        index < expression.length &&
                        expression[index] !in BINARY_OPERATORS &&
                        expression[index] != '(' &&
                        expression[index] != ')'
                    ) {
                        index++
                    }

                    val numberText = expression.substring(start, index).trim()
                    val value = parseNumber(numberText)?.takeIf { it.isFinite() } ?: return null
                    tokens += MathToken.Number(value)
                    expectsOperand = false
                }
            }
        } else {
            when {
                character in BINARY_OPERATORS -> {
                    tokens += MathToken.Operator(character)
                    index++
                    expectsOperand = true
                }

                character == ')' -> {
                    tokens += MathToken.Parenthesis(character)
                    index++
                }

                else -> return null
            }
        }
    }

    return tokens.takeIf { it.isNotEmpty() && !expectsOperand }
}

private fun toPostfix(tokens: List<MathToken>): List<MathToken>? {
    val output = mutableListOf<MathToken>()
    val operators = mutableListOf<MathToken>()

    for (token in tokens) {
        when (token) {
            is MathToken.Number -> output += token
            is MathToken.Operator -> {
                while (operators.isNotEmpty()) {
                    val top = operators.last()
                    if (top !is MathToken.Operator || precedence(top.symbol) < precedence(token.symbol)) {
                        break
                    }
                    output += operators.removeAt(operators.lastIndex)
                }
                operators += token
            }

            is MathToken.Parenthesis -> when (token.symbol) {
                '(' -> operators += token
                ')' -> {
                    while (operators.lastOrNull() is MathToken.Operator) {
                        output += operators.removeAt(operators.lastIndex)
                    }
                    if (operators.lastOrNull() != MathToken.Parenthesis('(')) {
                        return null
                    }
                    operators.removeAt(operators.lastIndex)
                }
            }
        }
    }

    while (operators.isNotEmpty()) {
        if (operators.last() is MathToken.Parenthesis) {
            return null
        }
        output += operators.removeAt(operators.lastIndex)
    }
    return output
}

private fun evaluate(tokens: List<MathToken>): Double? {
    val values = mutableListOf<Double>()

    for (token in tokens) {
        when (token) {
            is MathToken.Number -> values += token.value
            is MathToken.Operator -> {
                if (values.size < 2) {
                    return null
                }
                val right = values.removeAt(values.lastIndex)
                val left = values.removeAt(values.lastIndex)
                val result = when (token.symbol) {
                    '+' -> left + right
                    '-' -> left - right
                    '*' -> left * right
                    '/' -> if (right == 0.0) return null else left / right
                    '^' -> left.pow(right)
                    else -> return null
                }
                if (!result.isFinite()) {
                    return null
                }
                values += result
            }

            is MathToken.Parenthesis -> return null
        }
    }

    return values.singleOrNull()
}

private fun precedence(operator: Char): Int = when (operator) {
    '^' -> 2
    '*', '/' -> 1
    '+', '-' -> 0
    else -> error("Unsupported NumberBox operator: $operator")
}

private val BINARY_OPERATORS = charArrayOf('+', '-', '*', '/', '^')

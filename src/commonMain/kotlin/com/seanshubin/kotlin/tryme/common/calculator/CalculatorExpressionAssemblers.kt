package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.parser.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.parser.Branch
import com.seanshubin.kotlin.tryme.common.parser.Leaf
import com.seanshubin.kotlin.tryme.common.parser.Tree

object CalculatorExpressionAssemblers {
    private fun expression(tree: Tree<Token>): Expression {
        require(tree.name == "expression")
        tree as Branch
        require(tree.parts.size == 2)
        val term = term(tree.parts[0])
        val termTail = termTail(tree.parts[1])
        val expression = combineTermWithTermTail(term, termTail)
        return expression
    }

    private fun combineTermWithTermTail(term: Expression, termTail: List<OperatorExpression>): Expression {
        var current = term
        termTail.forEach { termPart ->
            current = when (termPart.operator) {
                Token.Plus -> Expression.Plus(current, termPart.expression)
                Token.Minus -> Expression.Minus(current, termPart.expression)
                else -> throw RuntimeException("Operator ${termPart.operator} is not supported")
            }
        }
        return current
    }

    private fun term(tree: Tree<Token>): Expression {
        require(tree.name == "term")
        tree as Branch
        require(tree.parts.size == 2)
        val factor = factor(tree.parts[0])
        val factorTail = factorTail(tree.parts[1])
        val expression = combineFactorWithFactorTail(factor, factorTail)
        return expression
    }

    private fun factor(tree: Tree<Token>): Expression {
        return when (tree.name) {
            "number" -> number(tree)
            "expression-in-parenthesis" -> expressionInParenthesis(tree)
            else -> throw RuntimeException("'${tree.name}' not supported here")
        }
    }

    private fun factorTail(tree: Tree<Token>): List<OperatorExpression> {
        require(tree.name == "factor-tail")
        tree as Branch
        return tree.parts.map(::factorPart)
    }

    private fun combineFactorWithFactorTail(factor: Expression, factorTail: List<OperatorExpression>): Expression {
        var current = factor
        factorTail.forEach { operatorExpression ->
            current = when (operatorExpression.operator) {
                Token.Times -> Expression.Times(current, operatorExpression.expression)
                Token.Divide -> Expression.Divide(current, operatorExpression.expression)
                else -> throw RuntimeException("Operator ${operatorExpression.operator} is not supported")
            }
        }
        return current
    }

    private fun termPart(tree: Tree<Token>): OperatorExpression {
        require(tree.name == "term-part")
        tree as Branch
        require(tree.parts.size == 2)
        val operator = operator(tree.parts[0])
        val term = term(tree.parts[1])
        return OperatorExpression(operator, term)
    }

    private fun factorPart(tree: Tree<Token>): OperatorExpression {
        require(tree.name == "factor-part")
        tree as Branch
        require(tree.parts.size == 2)
        val operator = operator(tree.parts[0])
        val factor = factor(tree.parts[1])
        return OperatorExpression(operator, factor)
    }

    private fun termTail(tree: Tree<Token>): List<OperatorExpression> {
        require(tree.name == "term-tail")
        tree as Branch
        return tree.parts.map(::termPart)
    }

    private fun operator(tree: Tree<Token>): Token {
        tree as Leaf
        return tree.value
    }

    private fun number(tree: Tree<Token>): Expression {
        require(tree.name == "number")
        tree as Leaf
        val token = tree.value
        token as Token.Number
        return Expression.Number(token.value)
    }

    private fun expressionInParenthesis(tree: Tree<Token>): Expression {
        tree as Branch
        require(tree.parts.size == 3)
        require(tree.parts[0].name == "open-paren")
        require(tree.parts[1].name == "expression")
        require(tree.parts[2].name == "close-paren")
        return expression(tree.parts[1])
    }

    val assemble: (String, Tree<Token>) -> Expression = { name: String, tree: Tree<Token> ->
        when (name) {
            "expression" -> expression(tree)
            else -> throw RuntimeException("Unable to assemble a '$name'")
        }
    }

    fun cursor(s: String): AssemblingCursor<Token, Expression> {
        val expressionMatchCursor = CalculatorExpressionMatchers.cursor(s)
        return AssemblingCursor(expressionMatchCursor, assemble)
    }

    fun parse(s: String): Expression {
        val cursor = cursor(s)
        val expression = cursor.value
        if (!cursor.next().isEnd) {
            throw RuntimeException("Expected end at ${cursor.next().summary}")
        }
        return expression
    }

    fun eval(s: String): Int = parse(s).eval()
}

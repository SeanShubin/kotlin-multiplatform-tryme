package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.assembler.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.matcher.Branch
import com.seanshubin.kotlin.tryme.common.matcher.Leaf
import com.seanshubin.kotlin.tryme.common.matcher.Tree

data class TermPart(val operator: Token, val expression: Expression)

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

    private fun combineTermWithTermTail(term: Expression, termTail: List<TermPart>): Expression {
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
        return number(tree.parts[0])
    }


    private fun termPart(tree: Tree<Token>): TermPart {
        require(tree.name == "term-part")
        tree as Branch
        require(tree.parts.size == 2)
        val operator = operator(tree.parts[0])
        val term = term(tree.parts[1])
        return TermPart(operator, term)
    }

    private fun termTail(tree: Tree<Token>): List<TermPart> {
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
            throw RuntimeException("Expected end at ${cursor.next().detail}")
        }
        return expression
    }

    fun eval(s: String): Int = parse(s).eval()
}

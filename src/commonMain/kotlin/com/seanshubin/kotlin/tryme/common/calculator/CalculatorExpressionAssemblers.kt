package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.assembler.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.matcher.Branch
import com.seanshubin.kotlin.tryme.common.matcher.Leaf
import com.seanshubin.kotlin.tryme.common.matcher.Tree

object CalculatorExpressionAssemblers {
    private fun expression(tree: Tree<Token>): Expression {
        require(tree.name == "expression")
        tree as Branch
        require(tree.parts.size == 2)
        return term(tree.parts[0])
    }

    private fun term(tree: Tree<Token>): Expression {
        require(tree.name == "term")
        tree as Branch
        require(tree.parts.size == 2)
        return number(tree.parts[0])
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
}

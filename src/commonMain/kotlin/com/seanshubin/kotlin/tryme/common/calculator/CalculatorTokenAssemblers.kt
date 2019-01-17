package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.assembler.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.matcher.Tree

object CalculatorTokenAssemblers {
    private val number = { tree: Tree<Char> ->
        var value = 0
        tree.values().forEach {
            value = value * 10 + digitToInt(it)
        }
        Token.Number(value)
    }

    val assemble = { name: String, tree: Tree<Char> ->
        when (name) {
            "number" -> number(tree)
            "open-paren" -> Token.OpenParen
            "close-paren" -> Token.CloseParen
            "plus" -> Token.Plus
            "minus" -> Token.Minus
            "times" -> Token.Times
            "divide" -> Token.Divide
            "whitespace-block" -> Token.Whitespace
            else -> throw RuntimeException("Unable to assemble a '$name'")
        }
    }

    private fun digitToInt(char: Char): Int = char - '0'

    fun cursor(s: String): AssemblingCursor<Char, Token> {
        val tokenMatchCursor = CalculatorTokenMatchers.cursor(s)
        return AssemblingCursor(tokenMatchCursor, assemble)
    }
}

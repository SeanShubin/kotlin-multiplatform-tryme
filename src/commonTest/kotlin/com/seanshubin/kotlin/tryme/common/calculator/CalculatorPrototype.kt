package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.assembler.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.cursor.FilterValueCursor
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import com.seanshubin.kotlin.tryme.common.matcher.*

/*
start with a char cursor
convert chars to token parse trees
assemble token parse trees into tokens
now we have a token cursor
convert tokens into element parse trees
assemble element parse trees into elements
now we have an element iterator
*/


object CalculatorTokenMatchers {
    private val digit = OneOfChar("digit", "0123456789")
    private val number = OneOrMore("number", digit)
    private val plus = Value("plus", '+')
    private val openParen = Value("open-paren", '(')
    private val closeParen = Value("close-paren", ')')
    private val whitespaceChar = Value("whitespace-char", ' ')
    private val whitespaceBlock = OneOrMore("whitespace-block", whitespaceChar)
    val token = OneOf(
        "token",
        number,
        plus,
        openParen,
        closeParen,
        whitespaceBlock
    )
}

interface Token {
    data class Number(val value: Int) : Token
    object Plus : Token {
        override fun toString(): String = "Plus"
    }

    object OpenParen : Token {
        override fun toString(): String = "OpenParen"
    }

    object CloseParen : Token {
        override fun toString(): String = "CloseParen"
    }

    object Whitespace : Token {
        override fun toString(): String = "Whitespace"
    }
}

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
            "whitespace-block" -> Token.Whitespace
            else -> throw RuntimeException("Unable to assemble a '$name'")
        }
    }

    private fun digitToInt(char: Char): Int = char - '0'
}

fun main(args: Array<String>) {
    val s = "123 + 234 + (345 + 456)"
    val charCursor = RowColCursor.create(s)
    val tokenMatchCursor = MatchingCursor(charCursor, CalculatorTokenMatchers.token)
    val assemblingCursor = AssemblingCursor(tokenMatchCursor, CalculatorTokenAssemblers.assemble)
    val filterCursor = FilterValueCursor(assemblingCursor, Token.Whitespace)
//    val tokenMatches = tokenMatchCursor.reify()
//    tokenMatches.forEach(::println)
    val assemblyMatches = filterCursor.reify()
    assemblyMatches.forEach(::println)
}

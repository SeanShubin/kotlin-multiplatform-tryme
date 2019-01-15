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
    private val number = OneOrMore("number", ::get, "digit")
    private val plus = Value("plus", '+')
    private val minus = Value("minus", '-')
    private val times = Value("times", '*')
    private val divide = Value("divide", '/')
    private val openParen = Value("open-paren", '(')
    private val closeParen = Value("close-paren", ')')
    private val whitespaceChar = Value("whitespace-char", ' ')
    private val whitespaceBlock = OneOrMore("whitespace-block", ::get, "whitespace-char")
    private val token = OneOf(
        "token",
        ::get,
        "number",
        "plus",
        "minus",
        "times",
        "divide",
        "open-paren",
        "close-paren",
        "whitespace-block"
    )
    private val matcherList = listOf(
        digit, number, plus, minus, times, divide, openParen, closeParen, whitespaceChar, whitespaceBlock, token
    )
    private val matcherMap = matcherList.map { Pair(it.name, it) }.toMap()
    operator fun get(name: String): Matcher<Char> =
        matcherMap[name] ?: throw RuntimeException("matcher named '$name' not found")
}

interface Token {
    data class Number(val value: Int) : Token
    object Plus : Token {
        override fun toString(): String = "Plus"
    }

    object Minus : Token {
        override fun toString(): String = "Minus"
    }

    object Times : Token {
        override fun toString(): String = "Times"
    }

    object Divide : Token {
        override fun toString(): String = "Divide"
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
            "minus" -> Token.Minus
            "times" -> Token.Times
            "divide" -> Token.Divide
            "whitespace-block" -> Token.Whitespace
            else -> throw RuntimeException("Unable to assemble a '$name'")
        }
    }

    private fun digitToInt(char: Char): Int = char - '0'
}

object CalculatorExpressionMatchers {
    private val expression = Sequence("expression", ::get, "term", "term-tail")
    private val term = Sequence("term", ::get, "factor", "factor-tail")
    private val factor = Sequence("factor", ::get, "number", "expression-in-parenthesis")
    private val termTail = ZeroOrMore("term-tail", ::get, "term-part")
    private val termPart = Sequence("term-part", ::get, "term-operator", "term")
    private val termOperator = OneOf("term-operator", ::get, "plus", "minus")
    private val factorTail = ZeroOrMore("factor-tail", ::get, "factor-part")
    private val factorPart = Sequence("factor-part", ::get, "factor-operator", "factor")
    private val factorOperator = OneOf("factor-operator", ::get, "times", "divide")
    private val expressionInParenthesis =
        Sequence("expression-in-parenthesis", ::get, "open-paren", "expression", "close-paren")
    private val number: Matcher<Token> = ValueOfType("number", Token.Number::class)
    private val plus: Matcher<Token> = Value("plus", Token.Plus)
    private val minus: Matcher<Token> = Value("minus", Token.Minus)
    private val times: Matcher<Token> = Value("times", Token.Times)
    private val divide: Matcher<Token> = Value("divide", Token.Divide)
    private val openParen: Matcher<Token> = Value("open-paren", Token.OpenParen)
    private val closeParen: Matcher<Token> = Value("close-paren", Token.CloseParen)

    private val matcherList = listOf<Matcher<Token>>(
        expression, expressionInParenthesis, openParen, closeParen,
        term, termTail, termPart, termOperator,
        factor, factorTail, factorPart, factorOperator,
        number, plus, minus, times, divide
    )
    private val matcherMap = matcherList.map { Pair(it.name, it) }.toMap()
    operator fun get(name: String): Matcher<Token> =
        matcherMap[name] ?: throw RuntimeException("matcher named '$name' not found")

}

fun main(args: Array<String>) {
    val s = "123 + 234 + (345 + 456)"
    val charCursor = RowColCursor.create(s)
    val tokenMatchCursor = MatchingCursor(charCursor, CalculatorTokenMatchers["token"])
    val assemblingCursor = AssemblingCursor(tokenMatchCursor, CalculatorTokenAssemblers.assemble)
    val filterCursor = FilterValueCursor(assemblingCursor, Token.Whitespace)
    val expressionMachCursor = MatchingCursor(filterCursor, CalculatorExpressionMatchers["expression"])
//    val tokenMatches = tokenMatchCursor.reify()
//    tokenMatches.forEach(::println)
//    val assemblyMatches = filterCursor.reify()
//    assemblyMatches.forEach(::println)
    val expressionMatches = expressionMachCursor.reify()
    expressionMatches.forEach(::println)
}

package com.seanshubin.kotlin.tryme.common.calculator.arithmetic

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import com.seanshubin.kotlin.tryme.common.parser.*

object CalculatorTokenMatchers {
    private val digit = OneOfChar("digit", "0123456789")
    private val number = OneOrMore(
        "number",
        CalculatorTokenMatchers::get, "digit"
    )
    private val plus = Value("plus", '+')
    private val minus = Value("minus", '-')
    private val times = Value("times", '*')
    private val divide = Value("divide", '/')
    private val openParen = Value("open-paren", '(')
    private val closeParen = Value("close-paren", ')')
    private val whitespaceChar = Value("whitespace-char", ' ')
    private val whitespaceBlock = OneOrMore(
        "whitespace-block",
        CalculatorTokenMatchers::get, "whitespace-char"
    )
    private val token = OneOf(
        "token",
        CalculatorTokenMatchers::get,
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
        digit,
        number,
        plus,
        minus,
        times,
        divide,
        openParen,
        closeParen,
        whitespaceChar,
        whitespaceBlock,
        token
    )
    private val matcherMap = matcherList.map { Pair(it.name, it) }.toMap()
    operator fun get(name: String): Matcher<Char> =
        matcherMap[name] ?: throw RuntimeException("matcher named '$name' not found")

    fun cursor(s: String): MatchingCursor<Char> {
        val charCursor = RowColCursor.create(s)
        return MatchingCursor(charCursor, CalculatorTokenMatchers["token"])
    }
}

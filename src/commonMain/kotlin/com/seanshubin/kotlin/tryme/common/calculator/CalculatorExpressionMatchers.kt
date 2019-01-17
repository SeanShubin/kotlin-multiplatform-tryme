package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.FilterValueCursor
import com.seanshubin.kotlin.tryme.common.matcher.*

object CalculatorExpressionMatchers {
    private val expression = Sequence("expression", ::get, "term", "term-tail")
    private val term = Sequence("term", ::get, "factor", "factor-tail")
    private val factor = OneOf("factor", ::get, "number", "expression-in-parenthesis")
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

    fun cursor(s: String): MatchingCursor<Token> {
        val tokenAssemblingCursor = CalculatorTokenAssemblers.cursor(s)
        val filterCursor = FilterValueCursor(tokenAssemblingCursor, Token.Whitespace)
        return MatchingCursor(filterCursor, CalculatorExpressionMatchers["expression"])
    }
}

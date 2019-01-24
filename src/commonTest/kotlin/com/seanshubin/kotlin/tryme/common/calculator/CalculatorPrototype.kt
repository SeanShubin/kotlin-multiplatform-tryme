package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.FilteringCursor
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import com.seanshubin.kotlin.tryme.common.parser.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.parser.MatchingCursor

fun main(args: Array<String>) {
    val s = "123 + 234 + (345 + 456)"
    val charCursor = RowColCursor.create(s)
    val tokenMatchCursor = MatchingCursor(charCursor, CalculatorTokenMatchers["token"])
    val tokenAssemblingCursor = AssemblingCursor(
        tokenMatchCursor,
        CalculatorTokenAssemblers.assemble
    )
    val filterCursor = FilteringCursor(tokenAssemblingCursor, Token.Whitespace)
    val expressionMatchCursor = MatchingCursor(filterCursor, CalculatorExpressionMatchers["expression"])
    var cursor = expressionMatchCursor
    while (!cursor.isEnd) {
        cursor.value.tree.toLines(0).forEach(::println)
        cursor = cursor.next()
    }
    val expressionAssemblingCursor: AssemblingCursor<Token, Expression> =
        AssemblingCursor(
            expressionMatchCursor,
            CalculatorExpressionAssemblers.assemble
        )
    val reified = expressionAssemblingCursor.reify()
    reified.forEach(::println)
}

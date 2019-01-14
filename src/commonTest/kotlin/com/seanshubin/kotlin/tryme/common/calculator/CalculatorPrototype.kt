package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.assembler.AssemblingCursor
import com.seanshubin.kotlin.tryme.common.assembler.Token
import com.seanshubin.kotlin.tryme.common.assembler.Token.Companion.assemble
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

fun main(args: Array<String>) {
    val digitMatcher = OneOfChar("digit", "0123456789")
    val numberMatcher = OneOrMore("number", digitMatcher)
    val plusMatcher = Value("plus", '+')
    val openParenMatcher = Value("open-paren", '(')
    val closeParenMatcher = Value("close-paren", ')')
    val whitespaceCharMatcher = Value("whitespace-char", ' ')
    val whitespaceBlockMatcher = OneOrMore("whitespace-block", whitespaceCharMatcher)
    val tokenMatcher = OneOf(
        "token",
        numberMatcher,
        plusMatcher,
        openParenMatcher,
        closeParenMatcher,
        whitespaceBlockMatcher
    )
    val s = "123 + 234 + (345 + 456)"
    val charCursor = RowColCursor.create(s)
    val tokenMatchCursor = MatchingCursor(charCursor, tokenMatcher)
    val assemblingCursor = AssemblingCursor(tokenMatchCursor, ::assemble)
    val filterCursor = FilterValueCursor(assemblingCursor, Token.Companion.Whitespace)
//    val tokenMatches = tokenMatchCursor.reify()
//    tokenMatches.forEach(::println)
    val assemblyMatches = filterCursor.reify()
    assemblyMatches.forEach(::println)
}

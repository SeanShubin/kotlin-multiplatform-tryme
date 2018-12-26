package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.calculator.CharPredicates.isNumberChar
import com.seanshubin.kotlin.tryme.common.calculator.CharPredicates.isWordChar
import com.seanshubin.kotlin.tryme.common.cursor.DetailCursor
import com.seanshubin.kotlin.tryme.common.cursor.RowCol

data class Calculator(
    val cursor: DetailCursor<Char, RowCol>,
    val stack: Stack<Ast> = Stack(),
    val errorReason: String? = null
) {
    val errorMessage: String get() = "[${cursor.detail.row + 1}:${cursor.detail.col + 1}] $errorReason"
    private fun next(): Calculator = copy(cursor = cursor.next())
    private fun numberNext(): Calculator {
        val number = cursor.value.toInt() - '0'.toInt()
        val ast = Ast.Num(number)
        return copy(cursor = cursor.next(), stack = stack.push(ast))
    }

    private fun error(reason: String): Calculator = copy(errorReason = reason)
    private fun valueIs(value: Char): Boolean = cursor.valueIs(value)
    private fun isWord(): Boolean = cursor.valueIs(isWordChar)
    private fun isNumber(): Boolean = cursor.valueIs(isNumberChar)

    private fun addOp(): Calculator =
        if (valueIs('+') || valueIs('-')) next()
        else this

    private fun multOp(): Calculator =
        if (valueIs('*') || valueIs('/')) next()
        else this


    private fun factorTail(): Calculator =
        if (valueIs('*') || valueIs('/')) multOp().factor().factorTail()
        else this

    private fun factor(): Calculator =
        if (valueIs('(')) {
            val expr = next().expr()
            if (expr.valueIs(')')) expr.next()
            else expr.error("closing ')' expected")
        } else if (valueIs('-')) next().factor()
        else if (isWord()) next()
        else if (isNumber()) numberNext()
        else error("factor expected")

    private fun applyPlus(): Calculator = copy()

    private fun termTail(): Calculator =
        if (valueIs('+') || valueIs('-')) {
            val a = addOp()
            val b = a.term()
            val c = b.termTail()
            c
        } else this

    private fun term(): Calculator = factor().factorTail()

    fun expr(): Calculator = term().termTail()
}

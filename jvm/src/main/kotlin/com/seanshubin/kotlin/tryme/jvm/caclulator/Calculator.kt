package com.seanshubin.kotlin.tryme.jvm.caclulator

data class Calculator(val cursor: Cursor<Char, Int>, val error:String? = null) {
    private fun next(): Calculator = copy(cursor = cursor.next())
    private fun error(message:String): Calculator = copy(error = message)
    private fun valueIs(value: Char): Boolean = cursor.valueIs(value)
    private fun isWord(): Boolean = cursor.valueIs(StringCursor.word)
    private fun isNumber(): Boolean = cursor.valueIs(StringCursor.number)

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
                else error("closing ')' expected")
            } else if (valueIs('-')) next().factor()
            else if (isWord()) next()
            else if (isNumber()) next()
            else error("factor expected")

    private fun termTail(): Calculator =
            if (valueIs('+') || valueIs('-')) addOp().term().termTail()
            else this

    private fun term(): Calculator = factor().factorTail()

    fun expr(): Calculator = term().termTail()
}

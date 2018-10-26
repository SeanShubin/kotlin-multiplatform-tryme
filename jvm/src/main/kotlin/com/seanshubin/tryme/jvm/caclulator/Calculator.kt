package com.seanshubin.tryme.jvm.caclulator

data class Calculator(val cursor: Cursor<Char>) {
    private fun next(): Calculator = Calculator(cursor.next())
    private fun valueIs(value: Char): Boolean = cursor.valueIs(value)
    private fun isWord(): Boolean = cursor.valueIs(StringCursor.word)
    private fun isNumber(): Boolean = cursor.valueIs(StringCursor.number)

    private fun addOp(): Calculator {
        return if (valueIs('+') || valueIs('-')) next()
        else this
    }

    private fun multOp(): Calculator {
        return if (valueIs('*') || valueIs('/')) next()
        else this
    }


    private fun factorTail(): Calculator {
        return if (valueIs('*') || valueIs('/')) {
            return multOp().factor().factorTail()
        } else {
            this
        }
    }

    private fun factor(): Calculator {
        if (valueIs('(')) {
            if (next().expr().valueIs(')'))
                return next()
            else
                throw RuntimeException("closing ')' expected")
        } else if (valueIs('-')) {
            return next().factor()
        } else if (isWord())
            return next();
        else if (isNumber())
            return next()
        else
            throw RuntimeException("factor expected")
    }

    private fun termTail(): Calculator {
        return if (valueIs('+') || valueIs('-')) {
            addOp().term().termTail()
        } else {
            this
        }
    }

    private fun term(): Calculator = factor().factorTail()

    fun expr(): Calculator = term().termTail()
}
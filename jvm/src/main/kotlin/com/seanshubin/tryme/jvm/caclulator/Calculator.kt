package com.seanshubin.tryme.jvm.caclulator

data class Calculator(val cursor: Cursor<Char>) {
    fun next(): Calculator = Calculator(cursor.next())
    fun valueIs(value: Char): Boolean = cursor.valueIs(value)
    fun isWord(): Boolean = cursor.valueIs(StringCursor.word)
    fun isNumber(): Boolean = cursor.valueIs(StringCursor.number)

    fun addOp(): Calculator {
        return if (valueIs('+') || valueIs('-')) next()
        else this
    }

    fun multOp(): Calculator {
        return if (valueIs('*') || valueIs('/')) next()
        else this
    }


    fun factorTail(): Calculator {
        return if (valueIs('*') || valueIs('/')) {
            return multOp().factor().factorTail()
        } else {
            this
        }
    }

    fun factor(): Calculator {
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

    fun termTail(): Calculator {
        return if (valueIs('+') || valueIs('-')) {
            addOp().term().termTail()
        } else {
            this
        }
    }

    fun term(): Calculator = factor().factorTail()

    fun expr(): Calculator = term().termTail()
}
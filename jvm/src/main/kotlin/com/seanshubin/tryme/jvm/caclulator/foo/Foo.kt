package com.seanshubin.tryme.jvm.caclulator.foo

import com.seanshubin.tryme.jvm.caclulator.Cursor
import com.seanshubin.tryme.jvm.caclulator.StringCursor

class Foo(val cursor: Cursor<Char>) {
    fun next(): Foo = Foo(cursor.next())
    fun valueIs(value: Char): Boolean = cursor.valueIs(value)
    fun isWord(): Boolean = cursor.valueIs(StringCursor.word)
    fun isNumber(): Boolean = cursor.valueIs(StringCursor.number)

    fun addOp(): Foo {
        return if (valueIs('+') || valueIs('-')) next()
        else this
    }

    fun multOp(): Foo {
        return if (valueIs('*') || valueIs('/')) next()
        else this
    }


    fun factorTail(): Foo {
        return if (valueIs('*') || valueIs('/')) {
            return multOp().factor().factorTail()
        } else {
            this
        }
    }

    fun factor(): Foo {
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

    fun termTail(): Foo {
        return if (valueIs('+') || valueIs('-')) {
            addOp().term().termTail()
        } else {
            this
        }
    }

    fun term(): Foo = factor().factorTail()

    fun expr(): Foo = term().termTail()

    fun calculate(): Foo = expr()

    fun calculate(s: String): Foo {
        return Foo(StringCursor(s)).calculate()
    }

}
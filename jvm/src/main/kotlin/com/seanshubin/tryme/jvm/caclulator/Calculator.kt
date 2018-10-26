package com.seanshubin.tryme.jvm.caclulator

fun addOp(s: Cursor<Char>): Cursor<Char> {
    return if (s.valueIs('+') || s.valueIs('-')) s.next()
    else s
}

fun multOp(s: Cursor<Char>): Cursor<Char> {
    return if (s.valueIs('*') || s.valueIs('/')) s.next()
    else s
}


fun factorTail(s: Cursor<Char>): Cursor<Char> {
    return if (s.valueIs('*') || s.valueIs('/')) {
        val a = multOp(s)
        val b = factor(a)
        val c = factorTail(b)
        c
    } else {
        s
    }
}

fun factor(s: Cursor<Char>): Cursor<Char> {
    if (s.valueIs('(')) {
        val a = s.next()
        val b = expr(a)
        if (b.valueIs(')'))
            return b.next()
        else
            throw RuntimeException("closing ')' expected")
    } else if (s.valueIs('-')) {
        val a = s.next();
        return factor(a)
    } else if (s.valueIs(StringCursor.word))
        return s.next()
    else if (s.valueIs(StringCursor.number))
        return s.next()
    else
        throw RuntimeException("factor expected")
}

fun termTail(s: Cursor<Char>): Cursor<Char> {
    return if (s.valueIs('+') || s.valueIs('-')) {
        val a = addOp(s)
        val b = term(a)
        val c = termTail(b)
        c
    } else {
        s
    }
}

fun term(s: Cursor<Char>): Cursor<Char> {
    val a = factor(s)
    val b = factorTail(a)
    return b
}

fun expr(s: Cursor<Char>): Cursor<Char> {
    val a = term(s)
    val b = termTail(a)
    return b
}

fun calculate(s: Cursor<Char>): Cursor<Char> {
    return expr(s)
}

fun calculate(s: String): Cursor<Char> {
    return calculate(StringCursor(s))
}

fun main(args: Array<String>) {
    println(calculate("1+2+3"))
}
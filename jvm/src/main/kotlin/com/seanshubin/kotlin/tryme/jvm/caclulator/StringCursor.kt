package com.seanshubin.kotlin.tryme.jvm.caclulator

import java.util.function.Predicate

data class StringCursor(private val s: String, private val index: Int = 0) : Cursor<Char> {
    override val isEnd: Boolean get() = index >= s.length
    override val value: Char get() = s[index]
    override fun valueIs(compareTo: Char): Boolean = !isEnd && value == compareTo
    override fun valueIs(predicate: Predicate<Char>): Boolean = !isEnd && predicate.test(value)
    override fun next(): Cursor<Char> = copy(index = index + 1)
    override val pos: Int get() = index

    companion object {
        val word = Predicate<Char> { t -> Character.isAlphabetic(t.toInt()) }
        val number = Predicate<Char> { t -> Character.isDigit(t.toInt()) }
    }
}

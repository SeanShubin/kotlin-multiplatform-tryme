package com.seanshubin.kotlin.tryme.common.cursor

data class StringCursor(private val s: String, private val index: Int = 0) :
    Cursor<Char, Int> {
    override val value: Char get() = s[index]
    override val isEnd: Boolean get() = index >= s.length
    override fun valueIs(compareTo: Char): Boolean = !isEnd && value == compareTo
    override fun valueIs(predicate: (Char) -> Boolean): Boolean = !isEnd && predicate(value)
    override fun next(): Cursor<Char, Int> = copy(index = index + 1)
    override val detail: Int get() = index
}

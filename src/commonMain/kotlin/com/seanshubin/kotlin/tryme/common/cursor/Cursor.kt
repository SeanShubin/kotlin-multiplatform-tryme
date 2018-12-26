package com.seanshubin.kotlin.tryme.common.cursor

interface Cursor<ElementType> {
    val isEnd: Boolean
    val value: ElementType
    fun next(): Cursor<ElementType>
    fun backingCursor(): Cursor<ElementType>
    fun valueIs(compareTo: ElementType): Boolean = !isEnd && value == compareTo
    fun valueIs(predicate: (ElementType) -> Boolean): Boolean = !isEnd && predicate(value)
    fun between(that: Cursor<ElementType>): List<ElementType> {
        val results = mutableListOf<ElementType>()
        var current = this
        while (!current.isEnd && current.backingCursor() != that.backingCursor()) {
            results.add(current.value)
            current = current.next()
        }
        return results
    }
}

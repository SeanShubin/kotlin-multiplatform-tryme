package com.seanshubin.kotlin.tryme.common.cursor

interface Cursor<ElementType> {
    val isEnd: Boolean
    val value: ElementType
    val summary: String
    val backingCursor: Cursor<ElementType>
    fun next(): Cursor<ElementType>
    fun valueIs(compareTo: ElementType): Boolean = !isEnd && value == compareTo
    fun valueIs(predicate: (ElementType) -> Boolean): Boolean = !isEnd && predicate(value)
    fun between(that: Cursor<ElementType>): List<ElementType> {
        val results = mutableListOf<ElementType>()
        var current = this
        while (current.backingCursor != that.backingCursor) {
            if (current.isEnd) {
                throw RuntimeException("Unable to navigate from begin to end cursor")
            } else {
                results.add(current.value)
                current = current.next()
            }
        }
        return results
    }

    fun reify(): List<ElementType> {
        val results = mutableListOf<ElementType>()
        var current = this
        while (!current.isEnd) {
            results.add(current.value)
            current = current.next()
        }
        return results
    }
}

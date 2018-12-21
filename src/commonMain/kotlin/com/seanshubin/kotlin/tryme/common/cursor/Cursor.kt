package com.seanshubin.kotlin.tryme.common.cursor

interface Cursor<ElementType, DetailType> {
    val isEnd: Boolean
    val detail: DetailType
    val value: ElementType
    fun valueIs(compareTo: ElementType): Boolean
    fun valueIs(predicate: (ElementType) -> Boolean): Boolean
    fun next(): Cursor<ElementType, DetailType>
    fun between(that: Cursor<ElementType, DetailType>): List<ElementType> {
        val results = mutableListOf<ElementType>()
        var current = this
        while (!current.isEnd && current != that) {
            results.add(current.value)
            current = current.next()
        }
        return results
    }
}

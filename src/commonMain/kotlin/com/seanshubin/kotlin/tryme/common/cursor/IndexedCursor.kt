package com.seanshubin.kotlin.tryme.common.cursor

class IndexedCursor<ElementType>(
    private val iterator: Iterator<ElementType>,
    private val index: Int = 0
) : Cursor<ElementType, Int> {
    private val valueFromIterator: ElementType? = if (iterator.hasNext()) iterator.next() else null
    private var nextCursor: IndexedCursor<ElementType>? = null
    override val isEnd: Boolean
        get() = valueFromIterator == null
    override val detail: Int
        get() = index
    override val value: ElementType
        get() = valueFromIterator ?: throw RuntimeException("No value past end of iterator")

    override fun valueIs(compareTo: ElementType): Boolean =
        if (valueFromIterator == null) false
        else valueFromIterator == compareTo

    override fun valueIs(predicate: (ElementType) -> Boolean): Boolean =
        if (valueFromIterator == null) false
        else predicate(valueFromIterator)

    override fun next(): IndexedCursor<ElementType> {
        if (nextCursor == null) {
            nextCursor = IndexedCursor(iterator, index + 1)
        }
        return nextCursor!!
    }
}

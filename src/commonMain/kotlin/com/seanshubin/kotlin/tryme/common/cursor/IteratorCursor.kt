package com.seanshubin.kotlin.tryme.common.cursor

class IteratorCursor<ElementType> private constructor(private val iterator: Iterator<ElementType>) :
    Cursor<ElementType> {
    private val valueFromIterator: ElementType? = if (iterator.hasNext()) iterator.next() else null
    private var nextCursor: IteratorCursor<ElementType>? = null
    override val isEnd: Boolean get() = valueFromIterator == null
    override val value: ElementType get() = valueFromIterator ?: throw RuntimeException("No value past end of iterator")
    override fun backingCursor(): Cursor<ElementType> = this
    override fun next(): Cursor<ElementType> {
        if (isEnd) throw RuntimeException("No next() past end of iterator")
        if (nextCursor == null) {
            nextCursor = IteratorCursor(iterator)
        }
        return nextCursor!!
    }

    companion object {
        fun <ElementType> create(iterator: Iterator<ElementType>) = IteratorCursor(iterator)
        fun create(s: String): IteratorCursor<Char> = create(s.iterator())
    }
}

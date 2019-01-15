package com.seanshubin.kotlin.tryme.common.cursor

class IndexedCursor<ElementType> private constructor(
    private val cursor: Cursor<ElementType>,
    private val index: Int
) : DetailCursor<ElementType, Int> {
    override val detail: Int get() = index
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: ElementType get() = cursor.value
    override fun backingCursor(): Cursor<ElementType> = cursor.backingCursor()
    override fun next(): IndexedCursor<ElementType> = IndexedCursor(cursor.next(), index + 1)

    companion object {
        fun <ElementType> create(iterator: Iterator<ElementType>): IndexedCursor<ElementType> =
            IndexedCursor(IteratorCursor.create(iterator), 0)

        fun create(s: String): IndexedCursor<Char> = create(s.iterator())
    }
}

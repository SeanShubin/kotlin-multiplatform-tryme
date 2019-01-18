package com.seanshubin.kotlin.tryme.common.cursor

class IndexedCursor<ElementType> private constructor(
    private val cursor: Cursor<ElementType>,
    private val index: Int
) : Cursor<ElementType> {
    override val summary: String get() = "[${index + 1}]"
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: ElementType get() = cursor.value
    override val backingCursor: Cursor<ElementType> get() = cursor.backingCursor

    override fun next(): IndexedCursor<ElementType> = IndexedCursor(cursor.next(), index + 1)

    companion object {
        fun <ElementType> create(iterator: Iterator<ElementType>): IndexedCursor<ElementType> =
            IndexedCursor(IteratorCursor.create(iterator), 0)

        fun create(s: String): IndexedCursor<Char> = create(s.iterator())
    }
}

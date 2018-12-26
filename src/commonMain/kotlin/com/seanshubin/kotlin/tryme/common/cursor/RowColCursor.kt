package com.seanshubin.kotlin.tryme.common.cursor

class RowColCursor<ElementType> private constructor(
    private val cursor: Cursor<ElementType>,
    private val rowEndMarker: ElementType,
    private val rowCol: RowCol
) : DetailCursor<ElementType, RowCol> {
    private var nextCursor: RowColCursor<ElementType>? = null
    override val detail: RowCol get() = rowCol
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: ElementType get() = cursor.value
    override fun backingCursor(): Cursor<ElementType> = cursor.backingCursor()

    override fun next(): RowColCursor<ElementType> {
        if (nextCursor == null) {
            nextCursor = if (valueIs(rowEndMarker)) {
                RowColCursor(cursor.next(), rowEndMarker, RowCol(rowCol.row + 1, 0))
            } else {
                RowColCursor(cursor.next(), rowEndMarker, RowCol(rowCol.row, rowCol.col + 1))
            }
        }
        return nextCursor!!
    }

    companion object {
        fun <ElementType> create(iterator: Iterator<ElementType>, rowEndMarker: ElementType) =
            create(IteratorCursor.create(iterator), rowEndMarker)

        fun <ElementType> create(cursor: Cursor<ElementType>, rowEndMarker: ElementType) =
            RowColCursor(cursor, rowEndMarker, RowCol(0, 0))

        fun create(s: String): RowColCursor<Char> = create(NormalizeNewlineCursor.create(s), '\n')
    }
}

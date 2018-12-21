package com.seanshubin.kotlin.tryme.common.cursor

class RowColCursor<ElementType>(
    private val iterator: Iterator<ElementType>,
    private val rowEndMarker: ElementType,
    private val rowCol: RowCol = RowCol(0, 0)
) : Cursor<ElementType, RowCol> {
    private val valueFromIterator: ElementType? = if (iterator.hasNext()) iterator.next() else null
    private var nextCursor: RowColCursor<ElementType>? = null
    override val isEnd: Boolean
        get() = valueFromIterator == null
    override val detail: RowCol
        get() = rowCol
    override val value: ElementType
        get() = valueFromIterator ?: throw RuntimeException("No value past end of iterator")

    override fun valueIs(compareTo: ElementType): Boolean =
        if (valueFromIterator == null) false
        else valueFromIterator == compareTo

    override fun valueIs(predicate: (ElementType) -> Boolean): Boolean =
        if (valueFromIterator == null) false
        else predicate(valueFromIterator)

    override fun next(): RowColCursor<ElementType> {
        if (nextCursor == null) {
            nextCursor = if (valueIs(rowEndMarker)) {
                RowColCursor(iterator, rowEndMarker, RowCol(rowCol.row + 1, 0))
            } else {
                RowColCursor(iterator, rowEndMarker, RowCol(rowCol.row, rowCol.col + 1))
            }
        }
        return nextCursor!!
    }
}

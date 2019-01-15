package com.seanshubin.kotlin.tryme.common.cursor

class FilterValueCursor<ElementType>(
    originalBackingCursor: RowColCursor<ElementType>,
    private val valueToSkip: ElementType
) :
    RowColCursor<ElementType> {
    private var backingCursor = originalBackingCursor

    init {
        while (backingCursor.valueIs(valueToSkip)) {
            backingCursor = backingCursor().next()
        }
    }

    override val isEnd: Boolean get() = backingCursor.isEnd
    override val value: ElementType get() = backingCursor.value
    override fun next(): FilterValueCursor<ElementType> = FilterValueCursor(backingCursor.next(), valueToSkip)
    override fun backingCursor(): RowColCursor<ElementType> = backingCursor
    override val detail: RowCol get() = backingCursor.detail
}

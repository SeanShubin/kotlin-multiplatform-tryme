package com.seanshubin.kotlin.tryme.common.cursor

class FilterValueCursor<ElementType>(
    originalBackingCursor: Cursor<ElementType>,
    private val valueToSkip: ElementType
) :
    Cursor<ElementType> {
    private var backingCursor = originalBackingCursor

    init {
        while (backingCursor.valueIs(valueToSkip)) {
            backingCursor = backingCursor().next()
        }
    }

    override val isEnd: Boolean get() = backingCursor.isEnd
    override val value: ElementType get() = backingCursor.value
    override fun next(): Cursor<ElementType> = FilterValueCursor(backingCursor.next(), valueToSkip)
    override fun backingCursor(): Cursor<ElementType> = backingCursor
}
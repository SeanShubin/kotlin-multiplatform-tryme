package com.seanshubin.kotlin.tryme.common.cursor

class FilterValueCursor<ElementType>(
    originalBackingCursor: Cursor<ElementType>,
    private val valueToSkip: ElementType
) :
    Cursor<ElementType> {
    private var mutableBackingCursor = originalBackingCursor
    override val backingCursor: Cursor<ElementType> get() = mutableBackingCursor

    init {
        while (mutableBackingCursor.valueIs(valueToSkip)) {
            mutableBackingCursor = mutableBackingCursor.next()
        }
    }

    override val summary: String get() = backingCursor.summary
    override val isEnd: Boolean get() = backingCursor.isEnd
    override val value: ElementType get() = backingCursor.value
    override fun next(): FilterValueCursor<ElementType> = FilterValueCursor(backingCursor.next(), valueToSkip)
}

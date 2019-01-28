package com.seanshubin.kotlin.tryme.common.cursor

class FilteringCursor<ElementType>(
    originalBackingCursor: Cursor<ElementType>,
    private val valueToSkip: ElementType
) :
    Cursor<ElementType> {
    private var lazyValue: ElementType? = null
    private var lazyNext: FilteringCursor<ElementType>? = null
    private var mutableBackingCursor = originalBackingCursor

    init {
        while (mutableBackingCursor.valueIs(valueToSkip)) {
            mutableBackingCursor = mutableBackingCursor.next()
        }
    }

    override val summary: String get() = mutableBackingCursor.summary
    override val isEnd: Boolean get() = mutableBackingCursor.isEnd
    override val value: ElementType
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): FilteringCursor<ElementType> {
        reifyLazy()
        return lazyNext!!
    }

    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = mutableBackingCursor.value
            lazyNext = FilteringCursor(mutableBackingCursor.next(), valueToSkip)
        }
    }

    companion object {
        fun <ElementType> create(
            iterator: Iterator<ElementType>,
            valueToSkip: ElementType
        ): FilteringCursor<ElementType> =
            FilteringCursor(IteratorCursor.create(iterator), valueToSkip)

        fun create(s: String, valueToSkip: Char): FilteringCursor<Char> = create(s.iterator(), valueToSkip)
    }
}

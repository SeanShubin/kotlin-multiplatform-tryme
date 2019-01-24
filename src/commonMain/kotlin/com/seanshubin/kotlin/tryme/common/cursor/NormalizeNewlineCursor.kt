package com.seanshubin.kotlin.tryme.common.cursor

data class NormalizeNewlineCursor constructor(val cursor: Cursor<Char>) : Cursor<Char> {
    private var lazyValue: Char? = null
    private var lazyNext: NormalizeNewlineCursor? = null
    override val summary: String get() = cursor.summary
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: Char
        get() {
            reifyLazy()
            return lazyValue!!
        }
    override fun next(): NormalizeNewlineCursor {
        reifyLazy()
        return lazyNext!!
    }

    override val backingCursor: Cursor<Char> get() = cursor.backingCursor

    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = if (cursor.valueIs('\r')) '\n' else cursor.value
            val next = cursor.next()
            lazyNext = if (next.valueIs('\r')) {
                val nextNext = next.next()
                if (nextNext.valueIs('\n')) {
                    NormalizeNewlineCursor(nextNext)
                } else {
                    NormalizeNewlineCursor(next)
                }
            } else {
                NormalizeNewlineCursor(next)
            }
        }
    }

    companion object {
        fun create(iterator: Iterator<Char>): NormalizeNewlineCursor =
            NormalizeNewlineCursor(IteratorCursor.create(iterator))

        fun create(s: String): NormalizeNewlineCursor = create(s.iterator())
    }
}

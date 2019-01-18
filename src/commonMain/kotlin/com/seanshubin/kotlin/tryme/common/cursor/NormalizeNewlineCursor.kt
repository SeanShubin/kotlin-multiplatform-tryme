package com.seanshubin.kotlin.tryme.common.cursor

data class NormalizeNewlineCursor constructor(val cursor: Cursor<Char>) : Cursor<Char> {
    override val summary: String get() = cursor.summary
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: Char get() = if (cursor.valueIs('\r')) '\n' else cursor.value
    override fun next(): NormalizeNewlineCursor {
        val nextCursor = cursor.next()
        return if (nextCursor.valueIs('\r')) {
            val nextNextCursor = nextCursor.next()
            return if (nextNextCursor.valueIs('\n')) {
                NormalizeNewlineCursor(nextNextCursor)
            } else {
                NormalizeNewlineCursor(nextCursor)
            }
        } else {
            NormalizeNewlineCursor(nextCursor)
        }
    }

    override val backingCursor: Cursor<Char> get() = cursor.backingCursor

    companion object {
        fun create(iterator: Iterator<Char>): NormalizeNewlineCursor =
            NormalizeNewlineCursor(IteratorCursor.create(iterator))

        fun create(s: String): NormalizeNewlineCursor = create(s.iterator())
    }
}

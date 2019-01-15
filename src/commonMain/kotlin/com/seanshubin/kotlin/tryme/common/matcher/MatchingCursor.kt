package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor
import com.seanshubin.kotlin.tryme.common.cursor.RowCol
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

class MatchingCursor<T>(val cursor: RowColCursor<T>, val matcher: Matcher<T>) : RowColCursor<Matched<T>> {
    private var lazyValue: Matched<T>? = null
    private var lazyNext: RowColCursor<T>? = null
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: Matched<T>
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): RowColCursor<Matched<T>> {
        reifyLazy()
        return MatchingCursor(lazyNext!!, matcher)
    }

    override fun backingCursor(): Cursor<Matched<T>> = this

    override val detail: RowCol get() = cursor.detail

    private fun reifyLazy() {
        if (lazyValue == null) {
            val result = matcher.checkMatch(cursor)
            when (result) {
                is Success -> {
                    lazyValue = Matched(result.name, result.value)
                    lazyNext = result.positionAfterMatch
                }
                is Failure -> {
                    throw MatchException(result)
                }
            }
        }
    }
}

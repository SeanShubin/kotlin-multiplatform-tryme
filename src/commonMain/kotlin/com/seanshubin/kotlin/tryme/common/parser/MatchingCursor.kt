package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class MatchingCursor<T>(val cursor: Cursor<T>, val matcher: Matcher<T>) : Cursor<Matched<T>> {
    private var lazyValue: Matched<T>? = null
    private var lazyNext: Cursor<T>? = null
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: Matched<T>
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): MatchingCursor<T> {
        reifyLazy()
        return MatchingCursor(lazyNext!!, matcher)
    }

    override val summary: String get() = cursor.summary

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

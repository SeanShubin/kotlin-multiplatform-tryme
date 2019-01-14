package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class MatchingCursor(val cursor: Cursor<Char>, val matcher: Matcher) : Cursor<Matched<Char>> {
    private var lazyValue: Matched<Char>? = null
    private var lazyNext: Cursor<Char>? = null
    override val isEnd: Boolean get() = cursor.isEnd
    override val value: Matched<Char>
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): Cursor<Matched<Char>> {
        reifyLazy()
        return MatchingCursor(lazyNext!!, matcher)
    }

    override fun backingCursor(): Cursor<Matched<Char>> = this

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

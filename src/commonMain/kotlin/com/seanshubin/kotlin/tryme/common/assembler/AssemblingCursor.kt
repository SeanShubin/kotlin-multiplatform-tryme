package com.seanshubin.kotlin.tryme.common.assembler

import com.seanshubin.kotlin.tryme.common.cursor.Cursor
import com.seanshubin.kotlin.tryme.common.matcher.Matched
import com.seanshubin.kotlin.tryme.common.matcher.Tree

class AssemblingCursor<T>(val cursor: Cursor<Matched<Char>>, val assemble: (String, Tree<Char>) -> T) : Cursor<T> {
    private var lazyValue: T? = null
    private var lazyNext: Cursor<T>? = null
    override val isEnd: Boolean
        get() = cursor.isEnd
    override val value: T
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): Cursor<T> {
        reifyLazy()
        return lazyNext!!
    }

    override fun backingCursor(): Cursor<T> = this
    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = assemble(cursor.value.name, cursor.value.tree)
            lazyNext = AssemblingCursor(cursor.next(), assemble)
        }
    }
}
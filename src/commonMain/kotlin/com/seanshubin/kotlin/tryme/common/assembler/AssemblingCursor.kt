package com.seanshubin.kotlin.tryme.common.assembler

import com.seanshubin.kotlin.tryme.common.cursor.RowCol
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import com.seanshubin.kotlin.tryme.common.matcher.Matched
import com.seanshubin.kotlin.tryme.common.matcher.Tree

class AssemblingCursor<T>(
    val cursor: RowColCursor<Matched<Char>>,
    val assemble: (String, Tree<Char>) -> T
) : RowColCursor<T> {
    private var lazyValue: T? = null
    private var lazyNext: AssemblingCursor<T>? = null
    override val isEnd: Boolean
        get() = cursor.isEnd
    override val value: T
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): AssemblingCursor<T> {
        reifyLazy()
        return lazyNext!!
    }

    override fun backingCursor(): AssemblingCursor<T> = this
    override val detail: RowCol get() = cursor.detail
    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = assemble(cursor.value.name, cursor.value.tree)
            lazyNext = AssemblingCursor(cursor.next(), assemble)
        }
    }
}
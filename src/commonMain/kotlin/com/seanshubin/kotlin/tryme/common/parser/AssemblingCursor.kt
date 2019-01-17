package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.RowCol
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

class AssemblingCursor<FromType, ToType>(
    val cursor: RowColCursor<Matched<FromType>>,
    val assemble: (String, Tree<FromType>) -> ToType
) : RowColCursor<ToType> {
    private var lazyValue: ToType? = null
    private var lazyNext: AssemblingCursor<FromType, ToType>? = null
    override val isEnd: Boolean
        get() = cursor.isEnd
    override val value: ToType
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): AssemblingCursor<FromType, ToType> {
        reifyLazy()
        return lazyNext!!
    }

    override fun backingCursor(): AssemblingCursor<FromType, ToType> = this
    override val detail: RowCol get() = cursor.detail
    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = assemble(cursor.value.name, cursor.value.tree)
            lazyNext = AssemblingCursor(cursor.next(), assemble)
        }
    }
}

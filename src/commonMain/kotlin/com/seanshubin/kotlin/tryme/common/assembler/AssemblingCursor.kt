package com.seanshubin.kotlin.tryme.common.assembler

import com.seanshubin.kotlin.tryme.common.cursor.Cursor
import com.seanshubin.kotlin.tryme.common.matcher.Matched
import com.seanshubin.kotlin.tryme.common.matcher.Tree

class AssemblingCursor(val cursor: Cursor<Matched<Char>>, val assemble: (String, Tree<Char>) -> Token) : Cursor<Token> {
    private var lazyValue: Token? = null
    private var lazyNext: Cursor<Token>? = null
    override val isEnd: Boolean
        get() = cursor.isEnd
    override val value: Token
        get() {
            reifyLazy()
            return lazyValue!!
        }

    override fun next(): Cursor<Token> {
        reifyLazy()
        return lazyNext!!
    }

    override fun backingCursor(): Cursor<Token> = this
    private fun reifyLazy() {
        if (lazyValue == null) {
            lazyValue = assemble(cursor.value.name, cursor.value.tree)
            lazyNext = AssemblingCursor(cursor.next(), assemble)
        }
    }
}
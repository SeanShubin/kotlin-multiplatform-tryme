package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

class OneOfChar(override val name: String, private val chars: String) : Matcher<Char> {
    override fun checkMatch(cursor: RowColCursor<Char>): Result<Char> {
        return when {
            cursor.isEnd -> Failure("Expected char in '$chars', got end of input", cursor)
            chars.contains(cursor.value) -> Success(name, Leaf(name, cursor.value), cursor.next())
            else -> Failure("Expected char in '$chars', got '${cursor.value}'", cursor)
        }
    }
}

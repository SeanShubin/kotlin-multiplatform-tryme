package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class OneOfValue<T>(override val name: String, private vararg val values: T) : Matcher<T> {
    override fun checkMatch(cursor: Cursor<T>): Result<T> {
        return when {
            cursor.isEnd -> {
                Failure("Expected one of ${values.joinToString(", ")}, got end of input", cursor)
            }
            values.contains(cursor.value) -> Success(name, Leaf(name, cursor.value), cursor.next())
            else -> Failure("Expected char in ${values.joinToString(", ")}, got '${cursor.value}'", cursor)
        }
    }
}

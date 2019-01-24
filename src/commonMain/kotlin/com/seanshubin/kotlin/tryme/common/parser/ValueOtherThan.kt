package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class ValueOtherThan<T>(override val name: String, private val notExpected: T) : Matcher<T> {
    override fun checkMatch(cursor: Cursor<T>): Result<T> {
        return when {
            cursor.isEnd -> Failure("Expected value other than '$notExpected', got end of input", cursor)
            cursor.valueIs(notExpected) -> Failure(
                "Expected value other than '$notExpected', got '${cursor.value}'",
                cursor
            )
            else -> Success(name, Leaf(name, cursor.value), cursor.next())
        }
    }
}

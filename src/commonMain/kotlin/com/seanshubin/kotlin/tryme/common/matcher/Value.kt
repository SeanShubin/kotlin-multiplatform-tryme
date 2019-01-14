package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class Value<T>(override val name: String, private val expected: T) : Matcher<T> {
    override fun checkMatch(cursor: Cursor<T>): Result<T> {
        return if (cursor.valueIs(expected)) {
            Success(name, Leaf(cursor.value), cursor.next())
        } else {
            Failure("Expected '$expected'", cursor)
        }
    }
}

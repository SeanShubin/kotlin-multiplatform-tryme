package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class Value(override val name: String, private val expected: Char) : Matcher {
    override fun checkMatch(cursor: Cursor<Char>): Result {
        return if (cursor.valueIs(expected)) {
            Success(name, Leaf(cursor.value), cursor.next())
        } else {
            Failure("Expected '$expected'", cursor)
        }
    }
}

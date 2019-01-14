package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class OneOf(override val name: String, private vararg val matchers: Matcher) : Matcher {
    override fun checkMatch(cursor: Cursor<Char>): Result {
        for (matcher in matchers) {
            val result = matcher.checkMatch(cursor)
            if (result is Success) {
                return result
            }
        }
        val names = matchers.map { it.name }
        val joinedNames = names.joinToString(", ")
        val message = "Expected one of: $joinedNames"
        return Failure(message, cursor)
    }
}

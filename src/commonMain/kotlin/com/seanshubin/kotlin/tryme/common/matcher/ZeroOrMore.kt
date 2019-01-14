package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class ZeroOrMore(override val name: String, val matcher: Matcher) : Matcher {
    override fun checkMatch(cursor: Cursor<Char>): Result {
        val successes = mutableListOf<Success>()
        var newCursor = cursor
        do {
            val result = matcher.checkMatch(newCursor)
            if (result is Success) {
                successes.add(result)
                newCursor = result.positionAfterMatch
            }
        } while (result is Success)
        val successTrees = successes.map { it.value }
        return Success(name, Branch(successTrees), newCursor)
    }
}

package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class OneOrMore(override val name: String, val matcher: Matcher) : Matcher {
    override fun checkMatch(cursor: Cursor<Char>): Result {
        val successes = mutableListOf<Success>()
        val firstMatch = matcher.checkMatch(cursor)
        return if (firstMatch is Success) {
            var newCursor = firstMatch.positionAfterMatch
            successes.add(firstMatch)
            do {
                val result = matcher.checkMatch(newCursor)
                if (result is Success) {
                    successes.add(result)
                    newCursor = result.positionAfterMatch
                }
            } while (result is Success)
            val successTrees = successes.map { it.value }
            Success(name, Branch(successTrees), newCursor)
        } else {
            firstMatch
        }
    }
}

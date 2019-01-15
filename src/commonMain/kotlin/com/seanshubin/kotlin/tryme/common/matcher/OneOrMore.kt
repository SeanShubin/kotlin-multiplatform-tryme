package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class OneOrMore<T>(
    override val name: String,
    private val lookup: (String) -> Matcher<T>,
    private val matcherName: String
) : Matcher<T> {
    override fun checkMatch(cursor: Cursor<T>): Result<T> {
        val matcher = lookup(matcherName)
        val successes = mutableListOf<Success<T>>()
        val firstMatch: Result<T> = matcher.checkMatch(cursor)
        return if (firstMatch is Success<*>) {
            firstMatch as Success<T>
            var newCursor = (firstMatch).positionAfterMatch
            successes.add(firstMatch)
            do {
                val result = matcher.checkMatch(newCursor)
                if (result is Success<*>) {
                    successes.add(result as Success<T>)
                    newCursor = result.positionAfterMatch
                }
            } while (result is Success<*>)
            val successTrees = successes.map { it.value }
            Success(name, Branch(successTrees), newCursor)
        } else {
            firstMatch
        }
    }
}

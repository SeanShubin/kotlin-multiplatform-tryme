package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

class Sequence<T>(
    override val name: String,
    private val lookup: (String) -> Matcher<T>,
    private vararg val matcherNames: String
) : Matcher<T> {
    override fun checkMatch(cursor: Cursor<T>): Result<T> = TODO()
}

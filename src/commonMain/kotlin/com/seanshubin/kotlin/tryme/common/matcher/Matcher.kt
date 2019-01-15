package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

interface Matcher<T> {
    val name: String
    fun checkMatch(cursor: RowColCursor<T>): Result<T>
}

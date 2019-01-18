package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

interface Matcher<T> {
    val name: String
    fun checkMatch(cursor: Cursor<T>): Result<T>
}

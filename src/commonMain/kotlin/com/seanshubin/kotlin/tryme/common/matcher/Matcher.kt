package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

interface Matcher {
    val name: String
    fun checkMatch(cursor: Cursor<Char>): Result
}

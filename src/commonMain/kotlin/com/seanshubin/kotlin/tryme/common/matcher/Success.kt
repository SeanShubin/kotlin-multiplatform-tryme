package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Success(val name: String, val value: Tree<Char>, val positionAfterMatch: Cursor<Char>) : Result

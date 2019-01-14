package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Failure(val message: String, val errorAtPosition: Cursor<Char>) : Result

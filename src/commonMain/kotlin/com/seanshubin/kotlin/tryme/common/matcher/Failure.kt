package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Failure<T>(val message: String, val errorAtPosition: Cursor<T>) : Result<T>

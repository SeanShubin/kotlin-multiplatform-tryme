package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Failure<T>(val message: String, val errorAtPosition: Cursor<T>) : Result<T> {
    override fun toString(): String = "${errorAtPosition.summary} $message"
}

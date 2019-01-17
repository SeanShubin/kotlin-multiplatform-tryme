package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

data class Failure<T>(val message: String, val errorAtPosition: RowColCursor<T>) : Result<T>
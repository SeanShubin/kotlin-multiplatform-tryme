package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

data class Success<T>(val name: String, val value: Tree<T>, val positionAfterMatch: RowColCursor<T>) : Result<T>

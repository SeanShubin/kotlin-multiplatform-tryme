package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Success<T>(val name: String, val value: Tree<T>, val positionAfterMatch: Cursor<T>) : Result<T>

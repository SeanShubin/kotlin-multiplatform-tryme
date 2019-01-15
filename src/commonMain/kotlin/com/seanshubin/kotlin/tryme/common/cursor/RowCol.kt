package com.seanshubin.kotlin.tryme.common.cursor

data class RowCol(val row: Int, val col: Int) {
    override fun toString(): String = "[${row + 1}:${col + 1}]"
}

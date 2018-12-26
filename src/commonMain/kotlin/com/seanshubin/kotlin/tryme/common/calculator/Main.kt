package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

fun main(args: Array<String>) {
    println(Calculator(RowColCursor.create("1+2+3")).expr())
}
package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursorImpl

fun main(args: Array<String>) {
    println(Calculator(RowColCursorImpl.create("1+2+3")).expr())
}
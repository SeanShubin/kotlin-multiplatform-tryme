package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.StringCursor


fun main(args: Array<String>) {
    println(Calculator(StringCursor("1+2+3")).expr())
}
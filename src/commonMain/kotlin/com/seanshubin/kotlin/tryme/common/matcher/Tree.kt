package com.seanshubin.kotlin.tryme.common.matcher

interface Tree<T> {
    val name: String
    fun values(): List<T>
    fun toLines(depth: Int): List<String>
    fun indent(s: String, depth: Int) = "  ".repeat(depth) + s
}

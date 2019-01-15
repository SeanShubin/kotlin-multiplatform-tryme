package com.seanshubin.kotlin.tryme.common.matcher

data class Leaf<T>(val value: T) : Tree<T> {
    override fun values(): List<T> = listOf(value)
    override fun toLines(depth: Int): List<String> = listOf(toLine(depth))
    private fun toLine(depth: Int): String = "  ".repeat(depth) + value
}

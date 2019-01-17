package com.seanshubin.kotlin.tryme.common.parser

data class Leaf<T>(override val name: String, val value: T) : Tree<T> {
    override fun values(): List<T> = listOf(value)
    override fun toLines(depth: Int): List<String> = listOf(
        indent(name, depth),
        indent(value.toString(), depth + 1)
    )
}

package com.seanshubin.kotlin.tryme.common.parser

data class Branch<T>(override val name: String, val parts: List<Tree<T>>) : Tree<T> {
    override fun values(): List<T> {
        return parts.flatMap { it.values() }
    }

    override fun toLines(depth: Int): List<String> =
        listOf(indent(name, depth)) + parts.flatMap { it.toLines(depth + 1) }
}

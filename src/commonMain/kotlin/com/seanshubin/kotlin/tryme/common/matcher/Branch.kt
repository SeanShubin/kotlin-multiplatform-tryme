package com.seanshubin.kotlin.tryme.common.matcher

data class Branch<T>(val parts: List<Tree<T>>) : Tree<T> {
    override fun values(): List<T> {
        return parts.flatMap { it.values() }
    }
}

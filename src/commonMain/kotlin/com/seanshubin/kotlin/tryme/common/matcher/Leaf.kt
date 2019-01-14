package com.seanshubin.kotlin.tryme.common.matcher

data class Leaf<T>(val value: T) : Tree<T> {
    override fun values(): List<T> = listOf(value)
}

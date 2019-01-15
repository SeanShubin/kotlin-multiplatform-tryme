package com.seanshubin.kotlin.tryme.common.matcher

interface Tree<T> {
    fun values(): List<T>
    fun toLines(depth: Int): List<String>
}

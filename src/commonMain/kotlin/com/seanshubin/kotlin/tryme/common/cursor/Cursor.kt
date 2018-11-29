package com.seanshubin.kotlin.tryme.common.cursor

interface Cursor<ElementType, DetailType> {
    val isEnd: Boolean
    val detail: DetailType
    val value: ElementType
    fun valueIs(compareTo: ElementType): Boolean
    fun valueIs(predicate: (Char) -> Boolean): Boolean
    fun next(): Cursor<ElementType, DetailType>
}

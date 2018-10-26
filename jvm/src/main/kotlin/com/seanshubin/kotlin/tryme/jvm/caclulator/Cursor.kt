package com.seanshubin.kotlin.tryme.jvm.caclulator

import java.util.function.Predicate

interface Cursor<ElementType, DetailType> {
    val isEnd: Boolean
    val detail:DetailType
    fun valueIs(compareTo: ElementType): Boolean
    fun valueIs(predicate: Predicate<ElementType>): Boolean
    fun next(): Cursor<ElementType, DetailType>
}

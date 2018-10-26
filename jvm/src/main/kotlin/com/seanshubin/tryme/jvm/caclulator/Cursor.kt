package com.seanshubin.tryme.jvm.caclulator

import java.util.function.Predicate

interface Cursor<T> {
    val isEnd: Boolean
    val value: T
    fun valueIs(compareTo: T): Boolean
    fun valueIs(predicate: Predicate<T>): Boolean
    fun next(): Cursor<T>
}

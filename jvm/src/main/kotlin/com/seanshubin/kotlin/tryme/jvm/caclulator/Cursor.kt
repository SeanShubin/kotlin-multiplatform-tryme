package com.seanshubin.kotlin.tryme.jvm.caclulator

import java.util.function.Predicate

interface Cursor<T> {
    val isEnd: Boolean
    val value: T
    val pos:Int
    fun valueIs(compareTo: T): Boolean
    fun valueIs(predicate: Predicate<T>): Boolean
    fun next(): Cursor<T>
}

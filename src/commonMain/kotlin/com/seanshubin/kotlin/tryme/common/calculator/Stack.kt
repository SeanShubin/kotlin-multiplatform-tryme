package com.seanshubin.kotlin.tryme.common.calculator

data class Stack<T>(var list: List<T> = emptyList()) {
    fun push(element: T): Stack<T> = Stack(list + listOf(element))
    fun pop(): Pair<Stack<T>, T> = Pair(Stack(list.subList(0, list.size - 1)), list[list.size - 1])
}

package com.seanshubin.kotlin.tryme.common.calculator

import kotlin.test.Test
import kotlin.test.assertEquals

class StackTest {
    @Test
    fun pushAndPop() {
        val stack = Stack<Int>()
        val (newStack, value) = stack.push(1).push(2).push(3).pop()
        assertEquals(Stack(listOf(1, 2)), newStack)
        assertEquals(3, value)
    }
}

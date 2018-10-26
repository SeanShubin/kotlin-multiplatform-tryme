package com.seanshubin.kotlin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonGreeterTest {
    @Test
    fun greetTest() {
        val greeter = CommonGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}

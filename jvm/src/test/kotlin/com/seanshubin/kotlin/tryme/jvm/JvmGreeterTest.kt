package com.seanshubin.kotlin.tryme.jvm

import kotlin.test.Test
import kotlin.test.assertEquals

class JvmGreeterTest {
    @Test
    fun greetTest() {
        val greeter = JvmGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}

package com.seanshubin.kotlin.tryme.js

import kotlin.test.Test
import kotlin.test.assertEquals

class JsGreeterTest {
    @Test
    fun greetTest() {
        val greeter = JsGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}

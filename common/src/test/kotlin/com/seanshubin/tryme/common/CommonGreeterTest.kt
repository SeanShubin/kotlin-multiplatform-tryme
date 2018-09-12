package com.seanshubin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonGreeterTest {
    @Test
    fun sayHello() {
        val expected = "Hello, world!"
        val greeter = CommonGreeter()
        val target = "world"
        val actual = greeter.greet(target)
        assertEquals(actual, expected)
    }
}

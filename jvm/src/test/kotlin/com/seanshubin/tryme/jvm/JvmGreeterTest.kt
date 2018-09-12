package com.seanshubin.tryme.jvm

import com.seanshubin.tryme.common.CommonGreeter
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmGreeterTest {
    @Test
    fun sayHello() {
        val expected = "Hello, world!"
        val greeter = JvmGreeter(CommonGreeter())
        val target = "world"
        val actual = greeter.greet(target)
        assertEquals(actual, expected)
    }
}

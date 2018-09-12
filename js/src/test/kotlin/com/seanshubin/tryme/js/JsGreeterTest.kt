package com.seanshubin.tryme.js

import com.seanshubin.tryme.common.CommonGreeter
import kotlin.test.Test
import kotlin.test.assertEquals

class JsGreeterTest {
    @Test
    fun sayHello() {
        val expected = "Hello, world!"
        val greeter = JsGreeter(CommonGreeter())
        val target = "world"
        val actual = greeter.greet(target)
        assertEquals(actual, expected)
    }

}
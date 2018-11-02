package com.seanshubin.kotlin.tryme.common.language

import kotlin.test.Test
import kotlin.test.assertEquals

class LanguageSamplesTest {
    @Test
    fun stringTemplates() {
        val target = "world"
        val greeting = "Hello, $target!"
        assertEquals("Hello, world!", greeting)
    }

    @Test
    fun stringTemplatesDollarSign() {
        assertEquals("dollar " + '$' + "ign", "dollar ${'$'}ign")
    }

    @Test
    fun codeBlock() {
        fun foo(x: Int, f: (Int) -> Int): Int = f(x)
        assertEquals(
            5,
            foo(2, { x: Int -> x + 3 })
        )
        assertEquals(5,
            foo(2) { x: Int -> x + 3 }
        )
        assertEquals(5,
            foo(2) { it + 3 }
        )
    }

    @Test
    fun codeBlockFunctionLiteralWithReceiver() {
        class Foo {
            val list: MutableList<String> = mutableListOf()
            fun bar(s: String) {
                list.add("bar $s")
            }

            fun baz(s: String) {
                list.add("baz $s")
            }
        }

        fun foo(codeBlock: Foo.() -> Unit): Foo {
            val foo = Foo()
            foo.codeBlock()
            return foo
        }
        assertEquals(
            listOf("bar a", "baz b"),
            foo {
                bar("a")
                baz("b")
            }.list
        )
    }

    @Test
    fun destructureDataClass() {
        data class Point(val x: Int, val y: Int)

        val p = Point(1, 2)
        val (x, y) = p
        assertEquals(1, x)
        assertEquals(2, y)
    }

    @Test
    fun destructureClass() {
        class Point(val x: Int, val y: Int){
            operator fun component1():Int = x
            operator fun component2():Int = y
        }

        val p = Point(1, 2)
        val (x, y) = p
        assertEquals(1, x)
        assertEquals(2, y)
    }
}

/*
getter/setter
operator
 */
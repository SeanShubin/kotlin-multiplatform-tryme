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
        class Point(val x: Int, val y: Int) {
            operator fun component1(): Int = x
            operator fun component2(): Int = y
        }

        val p = Point(1, 2)
        val (x, y) = p
        assertEquals(1, x)
        assertEquals(2, y)
    }

    @Test
    fun operator() {
        data class Point(val x: Int, val y: Int) {
            operator fun plus(that: Point) = Point(this.x + that.x, this.y + that.y)
        }

        val a = Point(1, 2)
        val b = Point(3, 4)
        val c = a + b
        assertEquals(Point(4, 6), c)
    }

    @Test
    fun getterSetter() {
        class Foo(initialValue: Int, val accessHistory: MutableList<String> = mutableListOf()) {
            private var currentValue: Int = initialValue
            val readOnly: Int
                get() {
                    accessHistory.add("readOnly $currentValue")
                    return currentValue
                }
            var readWrite: Int
                get() {
                    accessHistory.add("readWrite (get) $currentValue")
                    return currentValue
                }
                set(newValue: Int) {
                    accessHistory.add("readWrite (set) $currentValue -> $newValue")
                    currentValue = newValue
                }
        }

        val foo = Foo(123)
        val a = foo.readOnly
        val b = foo.readWrite
        foo.readWrite = 456
        val c = foo.readOnly
        val d = foo.readWrite

        assertEquals(123, a)
        assertEquals(123, b)
        assertEquals(456, c)
        assertEquals(456, d)
        val expectedAccessHistory = listOf(
            "readOnly 123",
            "readWrite (get) 123",
            "readWrite (set) 123 -> 456",
            "readOnly 456",
            "readWrite (get) 456"
        )
        assertEquals(expectedAccessHistory, foo.accessHistory)
    }

    @Test
    fun notQuitePatternMatch() {
        data class Coordinate(val x: Int, val y: Int)

        val baseline = Coordinate(1, 2)
        val sameAsBaseline = Coordinate(1, 2)
        val xDifferent = Coordinate(3, 2)
        val yDifferent = Coordinate(1, 4)
        val yDifferentCopy = yDifferent.copy(x = 5)

        fun describeCoordinate(coordinate: Coordinate): String {
            val (x, y) = coordinate
            return when {
                coordinate == Coordinate(1, 2) -> "looks like baseline"
                x == 1 -> "starts with 1"
                y == 2 -> "ends with 2, x is $x"
                else -> "another coordinate: values: x = $x, y = $y"
            }
        }

        assertEquals(describeCoordinate(baseline), "looks like baseline")
        assertEquals(describeCoordinate(sameAsBaseline), "looks like baseline")
        assertEquals(describeCoordinate(xDifferent), "ends with 2, x is 3")
        assertEquals(describeCoordinate(yDifferent), "starts with 1")
        assertEquals(describeCoordinate(yDifferentCopy), "another coordinate: values: x = 5, y = 4")
    }
}

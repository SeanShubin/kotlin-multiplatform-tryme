package com.seanshubin.kotlin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals

class BasicsTest {
    @Test
    fun partiallyApplyFunction() {
        fun makeNumber(a: Int, b: Int, c: Int): Int {
            return a * 100 + b * 10 + c
        }

        val fiveInTens = { a: Int, c: Int -> makeNumber(a, 5, c) }
        assertEquals(152, fiveInTens(1, 2))
    }

    @Test
    fun fold() {
        val list = listOf("a", "b", "c", "b", "a", "d", "a")
        fun addToHistogram(soFar: Map<String, Int>, next: String): Map<String, Int> {
            val oldValue = soFar[next] ?: 0
            val newValue = oldValue + 1
            return soFar.plus(Pair(next, newValue))
        }

        val empty: Map<String, Int> = emptyMap()
        val addToHistogramLambda = { a: Map<String, Int>, b: String -> addToHistogram(a, b) }
        val histogram: Map<String, Int> = list.fold(empty, addToHistogramLambda)
        assertEquals(mapOf(Pair("a", 3), Pair("b", 2), Pair("c", 1), Pair("d", 1)), histogram)
    }

    @Test
    fun patternMatch() {
        data class Coordinate(val x: Int, val y: Int)

        val baseline = Coordinate(1, 2)
        val sameAsBaseline = Coordinate(1, 2)
        val xDifferent = Coordinate(3, 2)
        val yDifferent = Coordinate(1, 4)
        val yDifferentCopy = yDifferent.copy(x = 5)

        //notice that pattern matching does not 'fall through' as is the case with switch statements in Java
        fun describeCoordinate(coordinate: Coordinate): String {
            val (x, y) = coordinate
            return when {
                coordinate == Coordinate(1, 2) -> "looks like baseline" //match the values exactly
                x == 1 -> "starts with 1"                               //match x to 1, don't care what y is
                y == 2 -> "ends with 2, x is $x"                        //match y to 2, recover the value for x
                else -> "another coordinate: values: x = $x, y = $y"    //get the values for x and y
            }
        }
        assertEquals("looks like baseline", describeCoordinate(baseline))
        assertEquals("looks like baseline", describeCoordinate(sameAsBaseline))
        assertEquals("ends with 2, x is 3", describeCoordinate(xDifferent))
        assertEquals("starts with 1", describeCoordinate(yDifferent))
        assertEquals("another coordinate: values: x = 5, y = 4", describeCoordinate(yDifferentCopy))
    }

    @Test
    fun dataClass() {
        data class Foo(val a: Int,
                       val b: Int,
                       val c: Int,
                       val d: Int,
                       val e: Int,
                       val f: Int,
                       val g: Int,
                       val h: Int,
                       val i: Int,
                       val j: Int,
                       val k: Int,
                       val l: Int,
                       val m: Int,
                       val n: Int,
                       val o: Int,
                       val p: Int,
                       val q: Int,
                       val r: Int,
                       val s: Int,
                       val t: Int,
                       val u: Int,
                       val v: Int,
                       val w: Int,
                       val x: Int,
                       val y: Int,
                       val z: Int
        )

        val foo = Foo(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6)
        val (_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, z) = foo
        assertEquals(6, z)
    }

    @Test
    fun map() {
        val pairs = listOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val mapA = mapOf(*pairs.toTypedArray())
        val mapB = mapOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        assertEquals(mapA, mapB)
    }

    @Test
    fun daysUntilNextTarget() {
        val data: List<Double> = listOf(
                214.2, 213.2, 212.0, 210.4, 212.0,
                212.0, 212.0, 212.0, 211.4, 211.6,
                211.6, 210.4, 209.0, 210.4, 209.0,
                210.4, 209.0, 209.0, 208.0)

        data class Builder(val soFar: List<Int>, val lastValue: Double, val counter: Int) {
            fun processValue(value: Double): Builder {
                return if (value < lastValue) {
                    copy(soFar = soFar + listOf(counter), lastValue = value, counter = 1)
                } else {
                    copy(soFar = soFar, lastValue = lastValue, counter = counter + 1)
                }
            }
        }

        val emptyBuilder = Builder(emptyList(), data[0], 0)
        val result = data.fold(emptyBuilder, { a, b -> a.processValue(b) })
        val actual = result.soFar
        val expected = listOf(1, 1, 1, 9, 6)
        assertEquals(expected, actual)
    }

    @Test
    fun nullHandling() {
        val possiblyNull: String? = "hello"
        val fromIf: String = if (possiblyNull == null) "null" else possiblyNull
        val fromElvis = possiblyNull ?: "null"
        assertEquals("hello", fromIf)
        assertEquals("hello", fromElvis)
    }
}

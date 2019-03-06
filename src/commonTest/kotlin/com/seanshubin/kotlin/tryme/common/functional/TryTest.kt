package com.seanshubin.kotlin.tryme.common.functional

import kotlin.test.Test
import kotlin.test.assertEquals

class TryTest {
    private fun no50s(tens: Int, ones: Int): Int =
        when (tens) {
            5 -> throw RuntimeException("50s not allowed")
            else -> tens * 10 + ones
        }

    private fun intToString(x: Int) = x.toString()
    private fun intToStringFail(x: Int): String {
        throw RuntimeException("intToStringFail")
    }

    private fun doubleDash(s: String) = "$s-$s"
    private fun doubleDashFail(s: String): String {
        throw RuntimeException("doubleDashFail")
    }

    @Test
    fun success() {
        // when
        val result = Try.invoke { no50s(4, 2) }

        // then
        assertEquals(Try.Success(42), result)
    }

    @Test
    fun failure() {
        // when
        val result = Try.invoke { no50s(5, 7) }

        // then
        result as Try.Failure
        assertEquals("50s not allowed", result.exception.message)
    }

    @Test
    fun mapSuccess() {
        // when
        val result = Try.invoke { 123 }.map(::intToString)

        // then
        result as Try.Success
        assertEquals("123", result.value)
    }

    @Test
    fun mapFailure() {
        // when
        val result = Try.invoke { 123 }.map(::intToStringFail)

        // then
        result as Try.Failure
        assertEquals("intToStringFail", result.exception.message)
    }

    @Test
    fun succeedFail() {
        // when
        val result = Try.invoke { intToString(123) }.flatMap { Try.invoke { doubleDashFail(it) } }

        // then
        result as Try.Failure
        assertEquals("doubleDashFail", result.exception.message)
    }

    @Test
    fun failSucceed() {
        // when
        val result = Try.invoke { intToStringFail(123) }.flatMap { Try.invoke { doubleDash(it) } }

        // then
        result as Try.Failure
        assertEquals("intToStringFail", result.exception.message)
    }

    @Test
    fun succeedSucceed() {
        // when
        val result = Try.invoke { intToString(123) }.flatMap { Try.invoke { doubleDash(it) } }

        // then
        result as Try.Success
        assertEquals("123-123", result.value)
    }

    @Test
    fun failFail() {
        // when
        val result = Try.invoke { intToStringFail(123) }.flatMap { Try.invoke { doubleDashFail(it) } }

        // then
        result as Try.Failure
        assertEquals("intToStringFail", result.exception.message)
    }
}

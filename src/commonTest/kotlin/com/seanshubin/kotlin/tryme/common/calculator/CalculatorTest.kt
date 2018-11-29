package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.StringCursor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CalculatorTest {
    @Test
    fun singleNumber() {
        val calculator = Calculator(StringCursor("1")).expr()
        assertEquals(1, calculator.cursor.detail)
        assertNull(calculator.error)
    }

    @Test
    fun simpleAddition() {
        val calculator = Calculator(StringCursor("1+2")).expr()
        assertEquals(3, calculator.cursor.detail)
        assertNull(calculator.error)
    }

    @Test
    fun errorAddition() {
        val calculator = Calculator(StringCursor("1+")).expr()
        assertEquals(2, calculator.cursor.detail)
        assertEquals("factor expected", calculator.error)
    }

    @Test
    fun missingCloseParen() {
        val calculator = Calculator(StringCursor("(1")).expr()
        assertEquals(2, calculator.cursor.detail)
        assertEquals("closing ')' expected", calculator.error)
    }
}

package com.seanshubin.kotlin.tryme.common.calculator

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CalculatorTest {
    @Test
    fun singleNumber() {
        val calculator = Calculator(RowColCursor.create("1")).expr()
        assertEquals(0, calculator.cursor.detail.row)
        assertEquals(1, calculator.cursor.detail.col)
        assertNull(calculator.errorReason)
    }

    @Test
    fun simpleAddition() {
        val calculator = Calculator(RowColCursor.create("1+2")).expr()
        assertEquals(0, calculator.cursor.detail.row)
        assertEquals(3, calculator.cursor.detail.col)
        assertNull(calculator.errorReason)
    }

    @Test
    fun errorAddition() {
        val calculator = Calculator(RowColCursor.create("1+")).expr()
        assertEquals(0, calculator.cursor.detail.row)
        assertEquals(2, calculator.cursor.detail.col)
        assertEquals("[1:3] factor expected", calculator.errorMessage)
    }

    @Test
    fun missingCloseParen() {
        val calculator = Calculator(RowColCursor.create("(1")).expr()
        assertEquals(0, calculator.cursor.detail.row)
        assertEquals(2, calculator.cursor.detail.col)
        assertEquals("[1:3] closing ')' expected", calculator.errorMessage)
    }
}

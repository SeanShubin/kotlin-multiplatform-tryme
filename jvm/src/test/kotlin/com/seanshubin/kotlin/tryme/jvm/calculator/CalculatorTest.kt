package com.seanshubin.kotlin.tryme.jvm.calculator

import com.seanshubin.kotlin.tryme.jvm.caclulator.Calculator
import com.seanshubin.kotlin.tryme.jvm.caclulator.StringCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {
    @Test
    fun singleNumber(){
        val calculator = Calculator(StringCursor("1")).expr()
        assertEquals(1, calculator.cursor.detail)
    }
    @Test
    fun simpleAddition(){
        val calculator = Calculator(StringCursor("1+2")).expr()
        assertEquals(3, calculator.cursor.detail)
    }
}
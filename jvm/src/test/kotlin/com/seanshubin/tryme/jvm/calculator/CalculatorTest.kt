package com.seanshubin.tryme.jvm.calculator

import com.seanshubin.tryme.jvm.caclulator.Calculator
import com.seanshubin.tryme.jvm.caclulator.StringCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {
    @Test
    fun singleNumber(){
        val calculator = Calculator(StringCursor("1")).expr()
        assertEquals(1, calculator.cursor.pos)
    }
}
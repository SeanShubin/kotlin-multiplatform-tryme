package com.seanshubin.kotlin.tryme.common.calculator

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {
    @Test
    fun number() {
        // given
        val input = "123"
        val expected = Expression.Number(123)

        // when
        val actual = CalculatorExpressionAssemblers.parse(input)

        // then
        assertEquals(expected, actual)
    }
}

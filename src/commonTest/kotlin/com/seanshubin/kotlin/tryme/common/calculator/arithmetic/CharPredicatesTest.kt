package com.seanshubin.kotlin.tryme.common.calculator.arithmetic

import kotlin.test.Test
import kotlin.test.assertEquals

class CharPredicatesTest {
    @Test
    fun checkIfNumber() {
        assertEquals(true, CharPredicates.isNumberChar('0'))
        assertEquals(true, CharPredicates.isNumberChar('5'))
        assertEquals(true, CharPredicates.isNumberChar('9'))
        assertEquals(false, CharPredicates.isNumberChar(' '))
        assertEquals(false, CharPredicates.isNumberChar('a'))
        assertEquals(false, CharPredicates.isNumberChar('-'))
    }

    @Test
    fun checkIfWord() {
        assertEquals(false, CharPredicates.isWordChar('0'))
        assertEquals(false, CharPredicates.isWordChar('_'))
        assertEquals(false, CharPredicates.isWordChar('9'))
        assertEquals(false, CharPredicates.isWordChar(' '))
        assertEquals(true, CharPredicates.isWordChar('a'))
        assertEquals(false, CharPredicates.isWordChar('-'))
    }
}

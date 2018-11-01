package com.seanshubin.kotlin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals

class LexicographicalComparatorTest {
    @Test
    fun compareBlanks() {
        assertEquals(compare("", ""), 0)
        assertEquals(compare("", "a"), -1)
        assertEquals(compare("a", ""), 1)
        assertEquals(compare("a", "a"), 0)
    }

    @Test
    fun compareNumbers() {
        assertEquals(compare("1", "2"), -1)
        assertEquals(compare("2", "2"), 0)
        assertEquals(compare("3", "2"), 1)
        assertEquals(compare("2", "10"), -1)
        assertEquals(compare("1", "01"), -1)
        assertEquals(compare("01", "1"), 1)
    }

    @Test
    fun compareEmpty() {
        assertEquals(compare("", "1"), -1)
        assertEquals(compare("", "a"), -1)
        assertEquals(compare("1", ""), 1)
        assertEquals(compare("a", ""), 1)
    }

    @Test
    fun compareText() {
        assertEquals(compare("a", "b"), -1)
        assertEquals(compare("b", "b"), 0)
        assertEquals(compare("c", "b"), 1)
    }

    @Test
    fun numbersLessThanText() {
        assertEquals(compare("1", "a"), -1)
        assertEquals(compare("a", "1"), 1)
    }

    @Test
    fun complexComparisons() {
        assertEquals(compare("abc123", "abc123"), 0)
        assertEquals(compare("abc123", "abc"), 1)
        assertEquals(compare("abc", "abc123"), -1)
        assertEquals(compare("123abc", "123abc"), 0)
        assertEquals(compare("123", "123abc"), -1)
        assertEquals(compare("123abc", "123"), 1)
    }

    private fun compare(left: String, right: String): Int = LexicographicalComparator.compare(left, right)
}

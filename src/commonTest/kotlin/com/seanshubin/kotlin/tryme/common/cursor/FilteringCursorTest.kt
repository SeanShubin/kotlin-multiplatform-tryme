package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.fail

class FilteringCursorTest {
    @Test
    fun startOutEmpty() {
        val s = ""
        val cursorEnd = FilteringCursor.create(s, 'b')
        assertEquals(true, cursorEnd.isEnd)
        try {
            cursorEnd.value
            fail("Should have thrown exception")
        } catch (ex: Exception) {
            assertEquals(ex.message, "No value past end of iterator")
        }

    }

    @Test
    fun filterValue() {
        val s = "abc"
        val cursorA = FilteringCursor.create(s, 'b')
        val cursorC = cursorA.next()
        val cursorEnd = cursorC.next()
        assertEquals('a', cursorA.value)
        assertEquals('c', cursorC.value)
        try {
            cursorEnd.value
            fail("Should have thrown exception")
        } catch (ex: Exception) {
            assertEquals(ex.message, "No value past end of iterator")
        }

    }

    @Test
    fun filterInitialValue() {
        val s = "abc"
        val cursorB = FilteringCursor.create(s, 'a')
        val cursorC = cursorB.next()
        val cursorEnd = cursorC.next()
        assertEquals('b', cursorB.value)
        assertEquals('c', cursorC.value)
        try {
            cursorEnd.value
            fail("Should have thrown exception")
        } catch (ex: Exception) {
            assertEquals(ex.message, "No value past end of iterator")
        }

    }

    @Test
    fun sameReference() {
        val s = "abc"
        val cursorA = FilteringCursor.create(s, 'd')
        val cursorB1 = cursorA.next()
        val cursorB2 = cursorA.next()
        val cursorC1 = cursorB1.next()
        val cursorC2 = cursorB2.next()

        assertSame(cursorB1, cursorB2)
        assertSame(cursorC1, cursorC2)
    }
}
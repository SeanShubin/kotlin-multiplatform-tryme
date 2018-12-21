package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.*

class IndexedCursorTest {
    @Test
    fun value() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = IndexedCursor(iterator)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorEnd = cursorC.next()
        assertEquals('a', cursorA.value)
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
    fun detail() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = IndexedCursor(iterator)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorEnd = cursorC.next()
        assertEquals(0, cursorA.detail)
        assertEquals(1, cursorB.detail)
        assertEquals(2, cursorC.detail)
        assertEquals(3, cursorEnd.detail)
    }

    @Test
    fun end() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = IndexedCursor(iterator)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorEnd = cursorC.next()
        assertFalse(cursorA.isEnd)
        assertFalse(cursorB.isEnd)
        assertFalse(cursorC.isEnd)
        assertTrue(cursorEnd.isEnd)
    }

    @Test
    fun valueIs() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorB = IndexedCursor(iterator).next()
        assertFalse(cursorB.valueIs('a'))
        assertTrue(cursorB.valueIs('b'))
    }

    @Test
    fun valueIsPredicate() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorB = IndexedCursor(iterator).next()
        assertFalse(cursorB.valueIs { it == 'a' })
        assertTrue(cursorB.valueIs { it == 'b' })
    }

    @Test
    fun immutable() {
        val s = "abc"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = IndexedCursor(iterator)
        val cursorB1 = cursorA.next()
        val cursorB2 = cursorA.next()
        val cursorC1 = cursorB1.next()
        val cursorC2 = cursorB2.next()

        assertEquals('b', cursorB1.value)
        assertEquals('b', cursorB2.value)
        assertEquals('c', cursorC1.value)
        assertEquals('c', cursorC2.value)

    }

    @Test
    fun between() {
        // given
        val s = "abcde"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = IndexedCursor(iterator)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorD = cursorC.next()
        val cursorE = cursorD.next()
        val expected = listOf('b', 'c', 'd')

        // when
        val actual = cursorB.between(cursorE)

        // then
        assertEquals(expected, actual)
    }
}

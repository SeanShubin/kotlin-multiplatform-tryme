package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class NormalizeNewlineCursorTest {
    @Test
    fun value() {
        val s = "a\nc\re\r\ng"
        val cursorA = NormalizeNewlineCursor.create(s)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorD = cursorC.next()
        val cursorE = cursorD.next()
        val cursorF = cursorE.next()
        val cursorG = cursorF.next()
        val cursorEnd = cursorG.next()
        assertEquals('a', cursorA.value)
        assertEquals('\n', cursorB.value)
        assertEquals('c', cursorC.value)
        assertEquals('\n', cursorD.value)
        assertEquals('e', cursorE.value)
        assertEquals('\n', cursorF.value)
        assertEquals('g', cursorG.value)
        try {
            cursorEnd.value
            fail("Should have thrown exception")
        } catch (ex: Exception) {
            assertEquals(ex.message, "No value past end of iterator")
        }
    }
}

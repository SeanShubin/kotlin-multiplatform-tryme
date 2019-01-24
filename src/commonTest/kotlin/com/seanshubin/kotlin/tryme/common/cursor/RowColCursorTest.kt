package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class RowColCursorTest {
    @Test
    fun value() {
        val s = "a\nc\re\r\ng"
        val cursorA = RowColCursor.create(s)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorD = cursorC.next()
        val cursorE = cursorD.next()
        val cursorF = cursorE.next()
        val cursorG = cursorF.next()
        assertEquals('a', cursorA.value)
        assertEquals('\n', cursorB.value)
        assertEquals('c', cursorC.value)
        assertEquals('\n', cursorD.value)
        assertEquals('e', cursorE.value)
        assertEquals('\n', cursorF.value)
        assertEquals('g', cursorG.value)
    }

    @Test
    fun detail() {
        val s = "a\nc\re\r\ng"
        val cursorA = RowColCursor.create(s)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorD = cursorC.next()
        val cursorE = cursorD.next()
        val cursorF = cursorE.next()
        val cursorG = cursorF.next()
        val cursorEnd = cursorG.next()
        assertEquals("[1:1]", cursorA.summary)
        assertEquals("[1:2]", cursorB.summary)
        assertEquals("[2:1]", cursorC.summary)
        assertEquals("[2:2]", cursorD.summary)
        assertEquals("[3:1]", cursorE.summary)
        assertEquals("[3:2]", cursorF.summary)
        assertEquals("[4:1]", cursorG.summary)
        assertEquals("[4:2]", cursorEnd.summary)
    }

    @Test
    fun sameReference() {
        val s = "abc"
        val cursorA = RowColCursor.create(s)
        val cursorB1 = cursorA.next()
        val cursorB2 = cursorA.next()
        val cursorC1 = cursorB1.next()
        val cursorC2 = cursorB2.next()

        assertSame(cursorB1, cursorB2)
        assertSame(cursorC1, cursorC2)
    }
}

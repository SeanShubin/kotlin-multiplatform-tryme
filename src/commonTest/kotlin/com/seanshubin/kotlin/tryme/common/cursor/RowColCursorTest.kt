package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.Test
import kotlin.test.assertEquals

class RowColCursorTest {
    @Test
    fun value() {
        val s = "a\nc\re\r\ng"
        val cursorA = RowColCursorImpl.create(s)
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
        val cursorA = RowColCursorImpl.create(s)
        val cursorB = cursorA.next()
        val cursorC = cursorB.next()
        val cursorD = cursorC.next()
        val cursorE = cursorD.next()
        val cursorF = cursorE.next()
        val cursorG = cursorF.next()
        val cursorEnd = cursorG.next()
        assertEquals(0, cursorA.detail.row)
        assertEquals(0, cursorA.detail.col)
        assertEquals(0, cursorB.detail.row)
        assertEquals(1, cursorB.detail.col)
        assertEquals(1, cursorC.detail.row)
        assertEquals(0, cursorC.detail.col)
        assertEquals(1, cursorD.detail.row)
        assertEquals(1, cursorD.detail.col)
        assertEquals(2, cursorE.detail.row)
        assertEquals(0, cursorE.detail.col)
        assertEquals(2, cursorF.detail.row)
        assertEquals(1, cursorF.detail.col)
        assertEquals(3, cursorG.detail.row)
        assertEquals(0, cursorG.detail.col)
        assertEquals(3, cursorEnd.detail.row)
        assertEquals(1, cursorEnd.detail.col)
    }
}

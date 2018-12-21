package com.seanshubin.kotlin.tryme.common.cursor

import kotlin.test.Test
import kotlin.test.assertEquals

class RowColCursorTest {
    @Test
    fun detail() {
        val s = "a\nb"
        val iterator: Iterator<Char> = s.iterator()
        val cursorA = RowColCursor(iterator, '\n')
        val cursorN = cursorA.next()
        val cursorB = cursorN.next()
        val cursorEnd = cursorB.next()
        assertEquals(0, cursorA.detail.row)
        assertEquals(0, cursorA.detail.col)
        assertEquals(0, cursorN.detail.row)
        assertEquals(1, cursorN.detail.col)
        assertEquals(1, cursorB.detail.row)
        assertEquals(0, cursorB.detail.col)
        assertEquals(1, cursorEnd.detail.row)
        assertEquals(1, cursorEnd.detail.col)
    }
}

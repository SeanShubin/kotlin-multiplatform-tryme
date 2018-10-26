package com.seanshubin.kotlin.tryme.jvm

import com.seanshubin.kotlin.tryme.jvm.compare.JvmLexicographicalComparator
import kotlin.test.Test
import kotlin.test.assertEquals

class LexicographicalComparatorTest {
    @Test
    fun compareNulls() {
        assertEquals(0, compare(null, null))
        assertEquals(-1, compare(null, ""))
        assertEquals(-1, compare(null, "a"))
        assertEquals(1, compare("", null))
        assertEquals(0, compare("", ""))
        assertEquals(-1, compare("", "a"))
        assertEquals(1, compare("a", null))
        assertEquals(1, compare("a", ""))
        assertEquals(0, compare("a", "a"))
    }

    private fun compare(left: String?, right: String?): Int =
            JvmLexicographicalComparator.compare(left, right)
}


package com.seanshubin.kotlin.tryme.common.compare

import com.seanshubin.kotlin.tryme.common.compare.NaturalSortUtil.splitNumbers
import kotlin.test.Test
import kotlin.test.assertEquals

class NaturalSortUtilTest {
    @Test
    fun splitNumbers() {
        assertEquals(splitNumbers(""), listOf())
        assertEquals(splitNumbers("abc"), listOf("abc"))
        assertEquals(splitNumbers("123"), listOf("123"))
        assertEquals(splitNumbers("abc123"), listOf("abc", "123"))
        assertEquals(splitNumbers("123abc"), listOf("123", "abc"))
        assertEquals(splitNumbers("abc123def456ghi789"), listOf("abc", "123", "def", "456", "ghi", "789"))
    }
}

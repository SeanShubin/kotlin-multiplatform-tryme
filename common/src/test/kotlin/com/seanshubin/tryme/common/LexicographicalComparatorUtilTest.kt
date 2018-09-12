package com.seanshubin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals
import com.seanshubin.tryme.common.LexicographicalComparatorUtil.splitNumbers


class LexicographicalComparatorUtilTest {
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

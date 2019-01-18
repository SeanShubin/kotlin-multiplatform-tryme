package com.seanshubin.kotlin.tryme.common

import com.seanshubin.kotlin.tryme.common.compare.ListDifference
import com.seanshubin.kotlin.tryme.common.format.StringUtil.toLines
import kotlin.test.assertEquals

object TestUtil {
    fun assertMultilineEquals(expected: String, actual: String) {
        assertMultilineEquals(expected.toLines(), actual.toLines())
    }

    fun assertMultilineEquals(expected: List<String>, actual: List<String>) {
        val difference = ListDifference.compare(
            "expected", expected,
            "actual  ", actual
        )
        val message = difference.messageLines.joinToString("\n")
        assertEquals(expected, actual, message)
    }
}
package com.seanshubin.kotlin.tryme.common.compare

import kotlin.test.Test
import kotlin.test.assertEquals

class ListDifferenceTest {
    @Test
    fun same() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2, 3)
        val difference = ListDifference.compare(a, b)
        val expectedMessage = listOf(
            "no differences",
            "same[0]: 1",
            "same[1]: 2",
            "same[2]: 3"
        )
        assertEquals(true, difference.isSame)
        assertEquals(expectedMessage, difference.messageLines)
    }

    @Test
    fun difference() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 4, 3)
        val expectedMessage = listOf(
            "different at index 1",
            "same[0]: 1",
            "different-a[1]: 2",
            "different-b[1]: 4",
            "remaining elements skipped"
        )
        val difference = ListDifference.compare(a, b)
        assertEquals(false, difference.isSame)
        assertEquals(expectedMessage, difference.messageLines)

    }

    @Test
    fun bShorter() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2)
        val expectedMessage = listOf(
            "different at index 2",
            "same[0]: 1",
            "same[1]: 2",
            "different-a[2]: 3",
            "different-b[2]: <missing>",
            "remaining elements skipped"
        )
        val difference = ListDifference.compare(a, b)
        assertEquals(false, difference.isSame)
        assertEquals(expectedMessage, difference.messageLines)

    }

    @Test
    fun aShorter() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2, 3, 4)
        val expectedMessage = listOf(
            "different at index 3",
            "same[0]: 1",
            "same[1]: 2",
            "same[2]: 3",
            "different-a[3]: <missing>",
            "different-b[3]: 4",
            "remaining elements skipped"
        )
        val difference = ListDifference.compare(a, b)
        assertEquals(false, difference.isSame)
        assertEquals(expectedMessage, difference.messageLines)
    }

    @Test
    fun multiLineStrings() {
        val a =
            """Some Line
              |Another Line
              |Different line A
              |Final line
              |""".trimMargin()
        val b =
            """Some Line
              |Another Line
              |Different line B
              |Final line
              |""".trimMargin()
        val expectedMessage = listOf(
            "different at index 2",
            "same[0]: Some Line",
            "same[1]: Another Line",
            "different-a[2]: Different line A",
            "different-b[2]: Different line B",
            "remaining elements skipped"
        )
        val difference = ListDifference.compare(a, b)
        assertEquals(false, difference.isSame)
        assertEquals(expectedMessage, difference.messageLines)
    }
}
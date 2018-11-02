package com.seanshubin.kotlin.tryme.common.format

import kotlin.test.Test
import kotlin.test.assertEquals

class MillisecondsFormatTest {
    @Test
    fun format() {
        verify("0 milliseconds", milliseconds(0))
        verify("1 millisecond", milliseconds(1))
        verify("999 milliseconds", milliseconds(999))
        verify("1 second", seconds(1))
        verify("1 second 1 millisecond", seconds(1) + milliseconds(1))
        verify("1 second 2 milliseconds", seconds(1) + milliseconds(2))
        verify("2 seconds 1 millisecond", seconds(2) + milliseconds(1))
        verify("2 seconds 2 milliseconds", seconds(2) + milliseconds(2))
        verify("1 minute", minutes(1))
        verify("2 minutes", minutes(2))
        verify("1 hour", hours(1))
        verify("2 hours", hours(2))
        verify("1 day", days(1))
        verify("2 days", days(2))
        verify("106751991167 days 7 hours 12 minutes 55 seconds 807 milliseconds", Long.MAX_VALUE)
    }

    private fun verify(expected: String, milliseconds: Long) {
        val actual = MillisecondsFormat.format(milliseconds)
        assertEquals(expected, actual, milliseconds.toString())
    }

    private fun milliseconds(x: Long): Long = x
    private fun seconds(x: Long): Long = milliseconds(x * 1000)
    private fun minutes(x: Long): Long = seconds(x * 60)
    private fun hours(x: Long): Long = minutes(x * 60)
    private fun days(x: Long): Long = hours(x * 24)
}

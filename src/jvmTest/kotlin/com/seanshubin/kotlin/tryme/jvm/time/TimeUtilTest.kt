package com.seanshubin.kotlin.tryme.jvm.time

import org.junit.Test
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals

class TimeUtilTest {
    @Test
    fun roundUpTo5Minutes() {
        // given
        val time = Instant.parse("2019-01-08T17:22:47.215657Z")
        val duration = Duration.of(5, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:25:00Z")

        // when
        val actual = TimeUtil.roundUpToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun roundDownTo5Minutes() {
        // given
        val time = Instant.parse("2019-01-08T17:22:47.215657Z")
        val duration = Duration.of(5, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:20:00Z")

        // when
        val actual = TimeUtil.roundDownToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun roundUpTo30Minutes() {
        // given
        val time = Instant.parse("2019-01-08T17:22:47.215657Z")
        val duration = Duration.of(30, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:30:00Z")

        // when
        val actual = TimeUtil.roundUpToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun roundDownTo30Minutes() {
        // given
        val time = Instant.parse("2019-01-08T17:22:47.215657Z")
        val duration = Duration.of(30, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:00:00Z")

        // when
        val actual = TimeUtil.roundDownToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun roundUpTo5MinutesWhenAlreadyRounded() {
        // given
        val time = Instant.parse("2019-01-08T17:25:00Z")
        val duration = Duration.of(5, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:25:00Z")

        // when
        val actual = TimeUtil.roundUpToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun roundDownTo5MinutesWhenAlreadyRounded() {
        // given
        val time = Instant.parse("2019-01-08T17:25:00Z")
        val duration = Duration.of(5, ChronoUnit.MINUTES)
        val expected = Instant.parse("2019-01-08T17:25:00Z")

        // when
        val actual = TimeUtil.roundDownToDuration(time, duration)

        // then
        assertEquals(expected, actual)
    }
}

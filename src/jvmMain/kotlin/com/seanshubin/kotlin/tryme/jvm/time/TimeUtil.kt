package com.seanshubin.kotlin.tryme.jvm.time

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

object TimeUtil {
    fun roundUpToDuration(time: Instant, duration: Duration): Instant {
        val roundedDown = roundDownToDuration(time, duration)
        return if (roundedDown == time) {
            roundedDown
        } else {
            roundedDown.plusNanos(duration.toNanos())
        }
    }

    fun roundDownToDuration(time: Instant, duration: Duration): Instant {
        val beginningOfDay = time.truncatedTo(ChronoUnit.DAYS)
        val sinceBeginningOfDay = Duration.between(beginningOfDay, time)
        val nanos = sinceBeginningOfDay.toNanos() / duration.toNanos() * duration.toNanos()
        return beginningOfDay.plusNanos(nanos)
    }
}

package com.seanshubin.tryme.common

import kotlin.test.Test
import kotlin.test.assertEquals

class MetricTest {
    @Test
    fun splitNumbers() {
        checkMilliseconds(9223372036310399999L, "106751991160 days 23 hours 59 minutes 59 seconds 999 milliseconds")
//        assertBackAndForth("1 day 10 hours 17 minutes 36 seconds 789 milliseconds")
//        assertBackAndForth("1 day 10 hours 36 seconds 789 milliseconds")
//        assertBackAndForth("10 hours 17 minutes 36 seconds 789 milliseconds")
//        assertBackAndForth("1 day 10 hours 17 minutes 36 seconds")
//        assertBackAndForth("17 minutes")
//        assertBackAndForth("789 milliseconds")
//        assertBackAndForth("1 day 5 hours 2 minutes 1 second 123 milliseconds")
//        assertBackAndForth("2 days 1 hour 1 minute 53 seconds 1 millisecond")
//        assertBackAndForth("25 days")
//        assertBackAndForth("0 milliseconds")

    }

    private fun checkMilliseconds(x:Long, s:String) {
//        val actualX = Metric.parseMilliseconds(s)
        val actualS = Metric.formatMilliseconds(x)
//        assertEquals(x, actualX)
        assertEquals(s, actualS)
    }
}

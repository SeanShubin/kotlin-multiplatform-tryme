package com.seanshubin.kotlin.tryme.jvm.async

import com.seanshubin.kotlin.tryme.common.format.MillisecondsFormat
import com.seanshubin.kotlin.tryme.jvm.timer.TimerFactory
import java.math.BigInteger

fun main(args: Array<String>) {
    val timer = TimerFactory.createDefault()
    val (duration, _) = timer.durationAndResult {
        (1000..10000 step 1000).forEach {
            val longValue = it.toLong()
            val bigIntegerValue = BigInteger.valueOf(longValue)
            val prime = Prime.nthPrime(bigIntegerValue)
            println(prime)
        }
    }
    val formattedTime = MillisecondsFormat.format(duration.toMillis())
    println(formattedTime) //9 seconds 42 milliseconds
}

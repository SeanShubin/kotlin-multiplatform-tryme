package com.seanshubin.tryme.jvm.async

import com.seanshubin.tryme.common.duration.MillisecondsFormat
import com.seanshubin.tryme.jvm.timer
import java.math.BigInteger

fun main(args: Array<String>) {
    val time = timer {
        (1000..10000 step 1000).forEach {
            val longValue = it.toLong()
            val bigIntegerValue = BigInteger.valueOf(longValue)
            val prime = Prime.nthPrime(bigIntegerValue)
            println(prime)
        }
    }
    val formattedTime = MillisecondsFormat.format(time)
    println(formattedTime)
}
/*
8380
8491
8380
8674
8492
*/

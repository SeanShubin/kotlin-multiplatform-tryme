package com.seanshubin.kotlin.tryme.jvm.async

import com.seanshubin.kotlin.tryme.common.MillisecondsFormat
import com.seanshubin.kotlin.tryme.jvm.timer
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

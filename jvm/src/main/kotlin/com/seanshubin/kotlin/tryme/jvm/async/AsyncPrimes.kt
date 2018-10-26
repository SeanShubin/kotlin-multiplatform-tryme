package com.seanshubin.kotlin.tryme.jvm.async

import com.seanshubin.kotlin.tryme.common.duration.MillisecondsFormat
import com.seanshubin.kotlin.tryme.jvm.timer
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.math.BigInteger

fun main(args: Array<String>) {
    val time = timer {
        val jobs = (1000..10000 step 1000).map {
            GlobalScope.launch {
                val longValue = it.toLong()
                val bigIntegerValue = BigInteger.valueOf(longValue)
                val prime = Prime.nthPrime(bigIntegerValue)
                println(prime)
            }
        }
        runBlocking {
            jobs.forEach { it.join() }
        }
    }
    val formattedTime = MillisecondsFormat.format(time)
    println(formattedTime)
}
/*
4484
4470
4864
4445
4552
*/
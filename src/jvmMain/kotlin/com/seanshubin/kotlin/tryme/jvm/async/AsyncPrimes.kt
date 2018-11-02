package com.seanshubin.kotlin.tryme.jvm.async

import com.seanshubin.kotlin.tryme.common.format.MillisecondsFormat
import com.seanshubin.kotlin.tryme.jvm.timer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    println(formattedTime) //4 seconds 659 milliseconds
}

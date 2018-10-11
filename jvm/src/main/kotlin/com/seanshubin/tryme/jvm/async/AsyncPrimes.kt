package com.seanshubin.tryme.jvm.async

import com.seanshubin.tryme.jvm.timer
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
    println("time = $time")
}
/*
4484
4470
4864
4445
4552
*/

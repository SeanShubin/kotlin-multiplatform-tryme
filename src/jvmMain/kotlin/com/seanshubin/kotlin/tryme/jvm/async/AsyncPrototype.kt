package com.seanshubin.kotlin.tryme.jvm.async

import kotlinx.coroutines.*

fun main(args: Array<String>) {
    suspend fun createFutureTwo(): Int {
        delay(100)
        return 2
    }

    suspend fun createFutureThree(): Int {
        delay(50)
        return 3
    }

    suspend fun compose(): Int {
        val two = createFutureTwo()
        val three = createFutureThree()
        return two * three
    }

    val foo: Deferred<Int> = GlobalScope.async {
        compose()
    }

    runBlocking {
        println(foo.await())
    }
}

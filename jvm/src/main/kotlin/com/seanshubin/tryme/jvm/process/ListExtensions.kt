package com.seanshubin.tryme.jvm.process

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

fun <T, U> List<T>.mapAsync(f: (T) -> U): List<U> {
    val jobs = this.map {
        GlobalScope.async { f(it) }
    }
    runBlocking {
        jobs.forEach { it.join() }
    }
    val results = jobs.map { it.getCompleted() }
    return results
}

fun List<String>.indent(prefix: String = "  "): List<String> = this.map { "$prefix$it" }

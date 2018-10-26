package com.seanshubin.kotlin.tryme.jvm.process

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun <T, U> List<T>.mapAsyncCoroutine(f: (T) -> U): List<U> {
    val jobs = this.map {
        GlobalScope.async { f(it) }
    }
    runBlocking {
        jobs.forEach { it.join() }
    }
    return jobs.map { it.getCompleted() }
}

fun <T, U> List<T>.mapAsyncCreateExecutorService(createExecutorService: () -> ExecutorService, f: (T) -> U): List<U> {
    fun toCall(input: T): Callable<U> = Callable { f(input) }
    val calls = this.map(::toCall)
    val executorService = createExecutorService()
    val futures = executorService.invokeAll(calls)
    fun toResult(future: Future<U>): U = future.get()
    val results = futures.map(::toResult)
    executorService.shutdown()
    return results
}

fun <T, U> List<T>.mapAsyncCachedThreadPool(f: (T) -> U): List<U> =
        this.mapAsyncCreateExecutorService({ Executors.newCachedThreadPool() }, f)

fun <T, U> List<T>.mapAsyncFixedThreadPool(numberOfThreads: Int, f: (T) -> U): List<U> =
        this.mapAsyncCreateExecutorService({ Executors.newFixedThreadPool(numberOfThreads) }, f)

fun List<String>.indent(prefix: String = "  "): List<String> = this.map { "$prefix$it" }

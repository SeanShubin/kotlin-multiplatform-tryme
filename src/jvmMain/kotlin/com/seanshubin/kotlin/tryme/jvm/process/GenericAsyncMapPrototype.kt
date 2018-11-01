package com.seanshubin.kotlin.tryme.jvm.process

fun squared(x: Int): Int = x * x

fun main(args: Array<String>) {
    val inputs: List<Int> = (1..3).toList()
    val outputsCoroutine = inputs.mapAsyncCoroutine(::squared)
    outputsCoroutine.forEach(::println)
    val outputsCachedThreadPool = inputs.mapAsyncCachedThreadPool(::squared)
    outputsCachedThreadPool.forEach(::println)
    val outputsFixedThreadPool = inputs.mapAsyncFixedThreadPool(3, ::squared)
    outputsFixedThreadPool.forEach(::println)
}

package com.seanshubin.tryme.jvm.process

fun squared(x: Int): Int = x * x

fun main(args: Array<String>) {
    val inputs: List<Int> = (1..10).toList()
    val outputs = inputs.mapAsync(::squared)
    outputs.forEach(::println)
}

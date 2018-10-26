package com.seanshubin.kotlin.tryme.jvm.process

import java.nio.file.Paths

fun main(args: Array<String>) {
    val processRunner = SystemProcessRunner()
    val command = listOf("git", "status", "-s")
    val directory = Paths.get("/Users/sshubin/github/sean/kotlin-tryme")
    val input = ProcessInput(command, directory)
    val output = processRunner.run(input)
    output.toMultipleLineString().forEach(::println)
}
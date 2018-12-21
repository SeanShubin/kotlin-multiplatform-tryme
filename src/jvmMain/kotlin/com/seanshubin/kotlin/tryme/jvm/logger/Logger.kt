package com.seanshubin.kotlin.tryme.jvm.logger

interface Logger {
    fun log(line: String) {
        log(listOf(line))
    }

    fun log(lines: List<String>)
}

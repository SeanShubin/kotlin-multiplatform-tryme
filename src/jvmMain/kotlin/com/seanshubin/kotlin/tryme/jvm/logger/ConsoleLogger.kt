package com.seanshubin.kotlin.tryme.jvm.logger

class ConsoleLogger : Logger {
    override fun log(lines: List<String>) {
        lines.forEach(::println)
    }
}

package com.seanshubin.kotlin.tryme.jvm.logger

import java.nio.file.Paths
import java.time.Clock

object LoggerFactory {
    fun create(name: String): Logger {
        val path = Paths.get("out", "log")
        val clock = Clock.systemDefaultZone()
        return ConsoleAndFileLogger(path, name, clock)
    }
}

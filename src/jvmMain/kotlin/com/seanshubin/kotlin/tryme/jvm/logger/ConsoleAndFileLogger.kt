package com.seanshubin.kotlin.tryme.jvm.logger

import java.nio.file.Path
import java.time.Clock

class ConsoleAndFileLogger(baseDir: Path, name: String, clock: Clock) : Logger {
    private val delegate = CompositeLogger(
        ConsoleLogger(),
        FileLogger(baseDir, name, clock)
    )

    override fun log(lines: List<String>) {
        delegate.log(lines)
    }
}

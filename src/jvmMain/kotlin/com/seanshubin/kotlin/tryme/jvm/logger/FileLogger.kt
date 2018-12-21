package com.seanshubin.kotlin.tryme.jvm.logger

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.time.Clock
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FileLogger(baseDir: Path, name: String, clock: Clock) : Logger {
    private val logFile: Path

    init {
        Files.createDirectories(baseDir)
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val fileNameSuffix = formattedDateTime.replace(':', '-')
        val fileName = "$name-$fileNameSuffix"
        logFile = baseDir.resolve(fileName)
    }

    override fun log(lines: List<String>) {
        Files.write(logFile, lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE)
    }
}

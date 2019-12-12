package com.seanshubin.kotlin.tryme.jvm.logger

import com.seanshubin.kotlin.tryme.jvm.contract.FilesContract
import com.seanshubin.kotlin.tryme.jvm.contract.FilesDelegate
import java.time.Clock
import java.nio.file.Path
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LoggerFactory(
    private val clock: Clock,
    private val files: FilesContract,
    private val emit: (String) -> Unit
) {
    fun createLogger(path: Path, name: String): Logger {
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val fileName = formattedDateTime.replace(':', '-').replace('.', '-') + "-$name"
        files.createDirectories(path)
        val logFile = path.resolve(fileName)
        return ConsoleAndFileLogger(emit, files, logFile)
    }

    fun createLogGroup(baseDir: Path): LogGroup {
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val logDir = baseDir.resolve(formattedDateTime.replace(':', '-').replace('.', '-'))
        return LogGroup(emit, files, logDir)
    }

    companion object {
        val instanceDefaultZone: LoggerFactory by lazy {
            val clock: Clock = Clock.systemDefaultZone()
            val files: FilesContract = FilesDelegate
            val emit: (String) -> Unit = ::println
            LoggerFactory(clock, files, emit)
        }
    }
}

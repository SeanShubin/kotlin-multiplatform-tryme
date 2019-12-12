package com.seanshubin.kotlin.tryme.jvm.logger

import com.seanshubin.kotlin.tryme.jvm.contract.FilesContract
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class FileLogger(
    private val files: FilesContract,
    private val logFile: Path
) : Logger {
    override fun log(lines: List<String>) {
        files.write(logFile, lines, StandardOpenOption.APPEND, StandardOpenOption.CREATE)
    }
}

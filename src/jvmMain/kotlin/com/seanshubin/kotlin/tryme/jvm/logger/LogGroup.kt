package com.seanshubin.kotlin.tryme.jvm.logger

import com.seanshubin.kotlin.tryme.jvm.contract.FilesContract
import java.nio.file.Path

class LogGroup(
    private val emit: (String) -> Unit,
    private val files: FilesContract,
    private val baseDir: Path
) {
    fun create(name: String, ext: String = "log"): Logger {
        files.createDirectories(baseDir)
        return ConsoleAndFileLogger(emit, files, baseDir.resolve("$name.$ext"))
    }
}

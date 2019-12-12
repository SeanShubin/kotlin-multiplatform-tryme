package com.seanshubin.kotlin.tryme.jvm.logger

import com.seanshubin.kotlin.tryme.jvm.contract.FilesContract
import java.nio.file.Path

class ConsoleAndFileLogger(
    emit: (String) -> Unit,
    files: FilesContract,
    logFile: Path
) :
    Logger {
    private val delegate = CompositeLogger(
        LineEmittingLogger(emit),
        FileLogger(files, logFile)
    )

    override fun log(lines: List<String>) {
        delegate.log(lines)
    }
}

package com.seanshubin.kotlin.tryme.jvm.logger

import com.seanshubin.kotlin.tryme.jvm.contract.FilesContract
import com.seanshubin.kotlin.tryme.jvm.contract.FilesDelegate
import java.nio.file.Paths
import java.time.Clock

object LoggerFactory {
    fun create(name: String): Logger {
        val path = Paths.get("out", "log")
        val clock = Clock.systemDefaultZone()
        val files: FilesContract = FilesDelegate
        val emit: (String) -> Unit = ::println
        return ConsoleAndFileLogger(emit, files, path, name, clock)
    }
}

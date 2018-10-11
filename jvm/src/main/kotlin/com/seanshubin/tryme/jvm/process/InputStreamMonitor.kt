package com.seanshubin.tryme.jvm.process

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

class InputStreamMonitor(name: String, inputStream: InputStream) {
    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val job: Job

    init {
        job = GlobalScope.launch {
            var ch = inputStream.read()
            while (ch != -1) {
                byteArrayOutputStream.write(ch)
                ch = inputStream.read()
            }
        }
    }

    fun linesUtf8(): List<String> {
        runBlocking {
            job.join()
        }
        val lines = String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8).split("\r\n", "\r", "\n")
        return if (lines.last().isEmpty()) {
            lines.take(lines.size - 1)
        } else {
            lines
        }
    }
}

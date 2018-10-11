package com.seanshubin.tryme.jvm.process

class SystemProcessRunner : ProcessRunner {
    override fun run(input: ProcessInput): ProcessOutput {
        val processBuilder = ProcessBuilder()
        processBuilder.command(*input.command.toTypedArray())
        processBuilder.directory(input.directory.toFile())
        val commandAsString = input.command.joinToString(" ")
        val directory = input.directory
        val name = "$directory> $commandAsString"
        val process = processBuilder.start()
        val outputStreamMonitor = InputStreamMonitor("($name):output", process.inputStream)
        val errorStreamMonitor = InputStreamMonitor("($name):error", process.errorStream)
        process.waitFor()
        return ProcessOutput(process.exitValue(), outputStreamMonitor.linesUtf8(), errorStreamMonitor.linesUtf8())
    }
}
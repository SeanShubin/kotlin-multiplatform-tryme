package com.seanshubin.tryme.jvm.process

data class ProcessOutput(val exitCode: Int,
                         val outputLines: List<String>,
                         val errorLines: List<String>) {
    fun toMultipleLineString(): List<String> =
            composeExitCode() + composeOutputLines() + composeErrorLines()

    private fun composeExitCode(): List<String> = listOf("exitCode = $exitCode")
    private fun composeOutputLines(): List<String> = listOf("outputLines (${outputLines.size})") + outputLines.indent()
    private fun composeErrorLines(): List<String> = listOf("errorLines (${errorLines.size})") + errorLines.indent()
}

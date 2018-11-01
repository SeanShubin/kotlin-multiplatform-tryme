package com.seanshubin.kotlin.tryme.jvm.process

import java.nio.file.Path

data class ProcessInput(val command: List<String>, val directory: Path) {
    fun toMultipleLineString(): List<String> = listOf("ProcessInput") + compose().indent()
    private fun compose(): List<String> = composeDirectory() + composeCommand()
    private fun composeDirectory(): List<String> = listOf("directory = $directory")
    private fun composeCommand(): List<String> = listOf("command") + listOf(command.joinToString(" ")).indent()
}

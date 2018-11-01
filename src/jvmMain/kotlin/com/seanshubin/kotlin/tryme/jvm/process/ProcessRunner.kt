package com.seanshubin.kotlin.tryme.jvm.process

interface ProcessRunner {
    fun run(input: ProcessInput): ProcessOutput
}

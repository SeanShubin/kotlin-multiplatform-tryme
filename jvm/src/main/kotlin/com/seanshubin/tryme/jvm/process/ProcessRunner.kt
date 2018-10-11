package com.seanshubin.tryme.jvm.process

interface ProcessRunner {
    fun run(input: ProcessInput): ProcessOutput
}

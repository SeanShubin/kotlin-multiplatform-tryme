package com.seanshubin.kotlin.tryme.jvm.http

import com.seanshubin.kotlin.tryme.jvm.timer.Timer
import java.net.http.HttpClient
import java.time.Clock

object HttpFactory{
    fun createDefault():Http{
        val client = HttpClient.newHttpClient()
        val clock = Clock.systemUTC()
        val timer = Timer(clock)
        return HttpExec(client, timer, { }, { _, _ -> })
    }
}

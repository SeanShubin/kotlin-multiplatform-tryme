package com.seanshubin.kotlin.tryme.jvm

import java.time.Clock
import java.time.Instant
import java.time.ZonedDateTime

fun main(vararg args: String) {
    val instantAsString = args[0]
    val instant = Instant.parse(instantAsString)
    val clock = Clock.systemDefaultZone()
    val zone = clock.zone
    val zonedDateTime = ZonedDateTime.ofInstant(instant, zone)
    println(zonedDateTime)
}

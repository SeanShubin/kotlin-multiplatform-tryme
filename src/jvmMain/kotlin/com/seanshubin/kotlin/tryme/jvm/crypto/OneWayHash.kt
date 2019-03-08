package com.seanshubin.kotlin.tryme.jvm.crypto

interface OneWayHash {
    fun hash(s: String): String
}

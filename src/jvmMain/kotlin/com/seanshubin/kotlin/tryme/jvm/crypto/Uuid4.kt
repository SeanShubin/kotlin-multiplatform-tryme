package com.seanshubin.kotlin.tryme.jvm.crypto

import java.util.*

class Uuid4 : UniqueIdGenerator {
    override fun uniqueId(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }
}

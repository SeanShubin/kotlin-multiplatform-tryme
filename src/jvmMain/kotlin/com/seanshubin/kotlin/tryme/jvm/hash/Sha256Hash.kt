package com.seanshubin.kotlin.tryme.jvm.hash

import com.seanshubin.kotlin.tryme.common.format.StringUtil.hex
import java.security.MessageDigest

class Sha256Hash : OneWayHash {
    private val messageDigest: MessageDigest by lazy {
        MessageDigest.getInstance("SHA-256")
    }

    override fun hash(s: String): String {
        val inputBytes = s.toByteArray()
        val hashedBytes = messageDigest.digest(inputBytes)
        val hashedString = hex(hashedBytes)
        return hashedString
    }
}

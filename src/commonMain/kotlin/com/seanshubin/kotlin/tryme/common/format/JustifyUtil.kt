package com.seanshubin.kotlin.tryme.common.format

object JustifyUtil {
    fun rightJustify(s: String, width: Int, padding: String = " "): String {
        return paddingFor(s, width, padding) + s
    }

    fun leftJustify(s: String, width: Int, padding: String = " "): String {
        return s + paddingFor(s, width, padding)
    }

    private fun paddingFor(s: String, width: Int, padding: String): String {
        val quantity = width - s.length
        return padding.repeat(quantity)
    }
}

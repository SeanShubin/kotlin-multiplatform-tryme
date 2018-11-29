package com.seanshubin.kotlin.tryme.common.format

object StringUtil {
    fun String.escape(): String = this.flatMap(::escapeCharToIterable).joinToString("")
    fun String.unescape(): String {
        val sb = StringBuilder()
        var index = 0
        var inEscapeSequence = false
        while (index < this.length) {
            val c = this[index]
            if (inEscapeSequence) {
                val unescaped = when (c) {
                    'n' -> '\n'
                    'b' -> '\b'
                    't' -> '\t'
                    'r' -> '\r'
                    '\"' -> '\"'
                    '\'' -> '\''
                    '\\' -> '\\'
                    else -> c
                }
                inEscapeSequence = false
                sb.append(unescaped)
            } else {
                when (c) {
                    '\\' -> inEscapeSequence = true
                    else -> sb.append(c)
                }
            }
            index++
        }
        if (inEscapeSequence) throw RuntimeException("Escape sequence started at end of string")
        return sb.toString()
    }

    fun String.toLines(): List<String> = this.split("\r\n", "\r", "\n")

    fun String.truncate(max: Int): String =
        if (this.length > max) "<${this.length} characters, showing first $max> ${this.substring(0, max)}"
        else this

    private fun escapeCharToString(target: Char): String =
        when (target) {
            '\n' -> "\\n"
            '\b' -> "\\b"
            '\t' -> "\\t"
            '\r' -> "\\r"
            '\"' -> "\\\""
            '\'' -> "\\\'"
            '\\' -> "\\\\"
            else -> target.toString()
        }

    private fun escapeCharToIterable(target: Char): Iterable<Char> = escapeCharToString(target).asIterable()
}

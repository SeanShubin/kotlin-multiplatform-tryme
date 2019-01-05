package com.seanshubin.kotlin.tryme.jvm.http

import java.util.regex.Pattern

data class Cookie(val name: String, val value: String, val attributes: Map<String, String?>) {
    companion object {
        private val cookiePartSplitPattern = Pattern.compile("""\s*;\s*""")

        fun parse(s: String): Cookie {
            val parts = s.split(cookiePartSplitPattern)
            val (name, value) = parseNameAndValue(parts[0])
            val attributePairs = parts.subList(1, parts.size).map(::parseAttributePair)
            return Cookie(name, value, attributePairs.toMap())
        }

        private fun parseNameAndValue(s: String): Pair<String, String> {
            val parts = split2(s, "=")
            return Pair(parts[0], parts[1])
        }

        private fun parseAttributePair(s: String): Pair<String, String?> {
            val parts = split1or2(s,"=")
            return when (parts.size) {
                1 -> Pair(parts[0], null)
                2 -> Pair(parts[0], parts[1])
                else -> throw RuntimeException("Expected 1 or 2 parts, got ${parts.size}")
            }
        }

        private fun split2(s:String, delimiter: String):List<String>{
            val parts = s.split(delimiter)
            return when (parts.size) {
                2 -> parts
                else -> throw RuntimeException("Expected 2 parts when splitting '$s' by '$delimiter', got ${parts.size}")
            }
        }

        private fun split1or2(s:String, delimiter: String):List<String>{
            val parts = s.split(delimiter)
            return when (parts.size) {
                1 -> parts
                2 -> parts
                else -> throw RuntimeException("Expected 1 or 2 parts when splitting '$s' by '$delimiter', got ${parts.size}")
            }
        }
    }
}

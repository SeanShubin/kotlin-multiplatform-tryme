package com.seanshubin.kotlin.tryme.jvm.http

data class Cookie(val name: String, val value: String, val attributes: String) {
    companion object {
        fun parse(s: String): Cookie {
            val firstSemicolonIndex = s.indexOf(';')
            if (firstSemicolonIndex == -1) throw RuntimeException("Expected ';' in '$s'")
            val firstPart = s.substring(0, firstSemicolonIndex)
            val attributes = s.substring(firstSemicolonIndex + 1).trim()
            val (name, value) = parseNameAndValue(firstPart)
            return Cookie(name, value, attributes)
        }

        private fun parseNameAndValue(s: String): Pair<String, String> {
            val firstEqualsIndex = s.indexOf('=')
            if (firstEqualsIndex == -1) throw RuntimeException("Expected '=' in '$s'")
            val firstPart = s.substring(0, firstEqualsIndex).trim()
            val secondPart = s.substring(firstEqualsIndex + 1).trim()
            return Pair(firstPart, secondPart)
        }
    }
}

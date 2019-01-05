package com.seanshubin.kotlin.tryme.jvm.http

import com.seanshubin.kotlin.tryme.jvm.collection.CollectionUtil.exactlyOne

data class Query(val elements:List<Pair<String, String?>>) {
    operator fun get(name:String):String =
        elements.filter { it.first.equals(name, ignoreCase = true)}.exactlyOne().second!!
    companion object {
        val String.query:Query get() = parseQueryFromUri(this)
        private fun parseQueryFromUri(s:String):Query {
            val parts = s.split("?")
            return parseQueryFromQueryString(parts[1])
        }
        private fun parseQueryFromQueryString(s:String):Query {
            val elements = s.split("&")
            return Query(elements.map(::parseQueryElement))
        }
        private fun parseQueryElement(s:String):Pair<String, String?> {
            val parts = s.split("=")
            return when(parts.size){
                1 -> Pair(parts[0], null)
                2 -> Pair(parts[0], parts[1])
                else -> throw RuntimeException("Expected 1 or 2 parts, got ${parts.size}")
            }
        }
    }
}

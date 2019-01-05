package com.seanshubin.kotlin.tryme.jvm.http

data class Request(val uri:String, val method:String, val body:ByteArray, val headers:List<Pair<String, String>>){
    fun toTable():List<List<Any>> =
        listOf(
            listOf("uri", uri),
            listOf("method", method),
            listOf("body", body)
        ) + headersToTable()

    private fun headersToTable():List<List<Any>> =
        headers.map{(name, value) -> listOf(name, value)}
}

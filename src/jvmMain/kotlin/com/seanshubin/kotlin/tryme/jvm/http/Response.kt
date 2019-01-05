package com.seanshubin.kotlin.tryme.jvm.http

import com.seanshubin.kotlin.tryme.jvm.collection.CollectionUtil.exactlyOne
import java.net.HttpURLConnection
import java.time.Duration

data class Response(
    val statusCode: Int,
    val body: ByteArray,
    val headers: List<Pair<String, String>>,
    val duration: Duration
) {
    val isRedirect: Boolean
        get() = when (statusCode) {
            HttpURLConnection.HTTP_MOVED_PERM, HttpURLConnection.HTTP_MOVED_TEMP -> true
            else -> false
        }

    val followRedirect: Request
        get() {
            val uri = headers.lookup("location")
            val method = "get"
            val body = ByteArray(0)
            return Request(uri, method, body, emptyList())
        }

    val cookies:Cookies
        get(){
            val cookieHeaders = headers.filter { it.first.equals("set-cookie", ignoreCase = true) }
            val cookieList = cookieHeaders.map { (_, cookieString) ->
                Cookie.parse(cookieString)
            }
            return Cookies(cookieList)
        }

    private fun List<Pair<String, String>>.lookup(name: String): String =
        this.filter { (key, _) -> name.equals(key, ignoreCase = true) }.exactlyOne().second

    fun toTable():List<List<Any>> =
        listOf(
            listOf("statusCode", statusCode),
            listOf("body", body),
            listOf("duration", duration)
        ) + headersToTable()

    private fun headersToTable():List<List<Any>> =
        headers.map{(name, value) -> listOf(name, value)}
}

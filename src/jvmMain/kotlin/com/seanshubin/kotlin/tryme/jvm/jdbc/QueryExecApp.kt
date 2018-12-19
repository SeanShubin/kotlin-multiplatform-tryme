package com.seanshubin.kotlin.tryme.jvm.jdbc

fun main(args: Array<String>) {
    val url = args[0]
    val user = args[1]
    val password = args[2]
    val query = args[3]
    val queryExec = QueryExec(url, user, password)
    queryExec.exec("command-line", query)
}
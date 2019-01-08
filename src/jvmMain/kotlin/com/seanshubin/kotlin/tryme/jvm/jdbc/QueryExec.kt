package com.seanshubin.kotlin.tryme.jvm.jdbc

class QueryExec(
    private val url: String,
    private val user: String,
    private val password: String,
    private val logTable: (List<List<Any?>>) -> Unit
) {
    fun exec(query: String) {
        val connectionLifecycle = JdbcConnectionLifecycle(url, user, password)
        val table = connectionLifecycle.withResultSet(query) { resultSet ->
            ResultSetUtil.toTable(resultSet)
        }
        logTable(table)
    }
}

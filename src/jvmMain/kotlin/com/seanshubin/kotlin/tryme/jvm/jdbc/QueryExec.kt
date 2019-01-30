package com.seanshubin.kotlin.tryme.jvm.jdbc

import com.seanshubin.kotlin.tryme.common.format.DurationFormat
import com.seanshubin.kotlin.tryme.jvm.timer.Timer


class QueryExec(
    private val url: String,
    private val user: String,
    private val password: String,
    private val timer: Timer,
    private val log: (String) -> Unit,
    private val logTable: (List<List<Any?>>) -> Unit
) {
    fun exec(query: String) {
        val connectionLifecycle = JdbcConnectionLifecycle(url, user, password)
        val (duration, table) = timer.durationAndResult {
            connectionLifecycle.withResultSet(query) { resultSet ->
                ResultSetUtil.toTable(resultSet)
            }
        }
        val millisecondsString = DurationFormat.milliseconds.format(duration.toMillis())
        log(query)
        log(millisecondsString)
        logTable(table)
    }
}

package com.seanshubin.kotlin.tryme.jvm.jdbc

import com.seanshubin.kotlin.tryme.common.format.RowStyleTableFormatter
import com.seanshubin.kotlin.tryme.common.format.TableFormatter
import com.seanshubin.kotlin.tryme.jvm.logger.ConsoleAndFileLogger
import java.nio.file.Paths
import java.time.Clock

fun main(args: Array<String>) {
    val url = args[0]
    val user = args[1]
    val password = args[2]
    val query = args[3]
    val name = "command-line"
    val tableFormatter =
        RowStyleTableFormatter.boxDrawing.copy(cellToString = TableFormatter.escapeAndTruncateString(100))
    val path = Paths.get("out", "log")
    val clock = Clock.systemDefaultZone()
    val logger = ConsoleAndFileLogger(path, name, clock)
    val logTable: (List<List<Any?>>) -> Unit = { data ->
        val table = tableFormatter.format(data)
        logger.log(table)
    }
    val queryExec = QueryExec(url, user, password, logTable)
    queryExec.exec(query)
}
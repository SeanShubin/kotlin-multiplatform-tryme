package com.seanshubin.kotlin.tryme.jvm.jdbc

import com.seanshubin.kotlin.tryme.common.format.TableFormatter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Clock
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class QueryExec(val url: String, val user: String, val password: String) {
    fun exec(name: String, query: String) {
        val connectionLifecycle = JdbcConnectionLifecycle(url, user, password)
        val table = connectionLifecycle.withResultSet(query) { resultSet ->
            ResultSetUtil.toTable(resultSet)
        }
        val tableFormatter = TableFormatter.boxDrawing.copy(cellToString = TableFormatter.escapeAndTruncateString(100))
        val lines = listOf(query) + tableFormatter.createTable(table)
        val baseDir = Paths.get("out", "results")
        Files.createDirectories(baseDir)
        val clock = Clock.systemDefaultZone()
        val now = clock.instant()
        val zone = clock.zone
        val zonedDateTime = ZonedDateTime.ofInstant(now, zone)
        val formattedDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
        val fileNameSuffix = formattedDateTime.replace(':', '-')
        val fileName = "$name-$fileNameSuffix"
        val logFile = baseDir.resolve(fileName)
        Files.write(logFile, lines, StandardCharsets.UTF_8)
        lines.forEach(::println)

    }
}

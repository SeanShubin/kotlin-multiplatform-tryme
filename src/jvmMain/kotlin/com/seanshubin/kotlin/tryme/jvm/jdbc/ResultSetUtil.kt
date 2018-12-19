package com.seanshubin.kotlin.tryme.jvm.jdbc

import java.sql.ResultSet

object ResultSetUtil {
    fun toTable(resultSet: ResultSet): List<List<Any>> {
        val columnNames = getColumnNames(resultSet)
        val data = getData(resultSet, columnNames)
        return listOf(columnNames) + data
    }

    private fun getColumnNames(resultSet: ResultSet): List<String> {
        val metaData = resultSet.metaData
        val columnCount = metaData.columnCount
        val extractColumnName = { columnIndex: Int -> metaData.getColumnName(columnIndex) }
        val columnNames = (1..columnCount).map(extractColumnName)
        return columnNames
    }

    private fun getData(resultSet: ResultSet, columnNames: List<String>): List<List<Any>> {
        val iterator: Iterator<List<Any>> = ResultSetIterator(resultSet, columnNames)
        val list = ArrayList<List<Any>>()
        iterator.forEachRemaining { list.add(it) }
        return list
    }
}

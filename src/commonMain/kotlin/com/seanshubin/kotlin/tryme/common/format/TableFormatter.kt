package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.format.ListUtil.transpose

class TableFormatter(val wantInterleave: Boolean,
                     val rowLeft: String = "║",
                     val rowCenter: String = "│",
                     val rowRight: String = "║") {

    interface Justify

    data class LeftJustify(val x: Any) : Justify

    data class RightJustify(val x: Any) : Justify

    fun createTable(originalRows: List<List<Any>>): List<String> {
        val paddedRows = makeAllRowsTheSameSize(originalRows, "")
        val columns = paddedRows.transpose()
        val columnWidths = columns.map { a: List<Any> -> maxWidthForColumn(a) }
        val formattedRows = formatRows(columnWidths, paddedRows)
        return if (wantInterleave) {
            val top = makeTop(columnWidths)
            val middle = makeMiddle(columnWidths)
            val bottom = makeBottom(columnWidths)
            listOf(top) + interleave(formattedRows, middle) + listOf(bottom)
        } else {
            formattedRows
        }
    }

    private fun makeAllRowsTheSameSize(rows: List<List<Any>>, value: Any): List<List<Any>> {
        val rowSizes = rows.map { row -> row.size }
        val targetSize = rowSizes.max() ?: 0

        fun makeRowSameSize(row: List<Any>): List<Any> {
            val extraCells = makeExtraCells(targetSize - row.size, value)
            return row + extraCells
        }

        return rows.map { makeRowSameSize(it) }
    }

    private fun makeExtraCells(howMany: Int, contents: Any): List<Any> {
        return (1..howMany).map { _ -> contents }
    }

    private fun emptyRow(size: Int): List<String> {
        return generateSequence { "" }.take(size).toList()
    }

    private fun makeTop(columnWidths: List<Int>): String {
        return makeRow(columnWidths, emptyRow(columnWidths.size), "═", "╔", "╤", "╗")
    }

    private fun makeMiddle(columnWidths: List<Int>): String {
        return makeRow(columnWidths, emptyRow(columnWidths.size), "─", "╟", "┼", "╢")
    }

    private fun makeBottom(columnWidths: List<Int>): String {
        return makeRow(columnWidths, emptyRow(columnWidths.size), "═", "╚", "╧", "╝")
    }

    private fun makeRow(columnWidths: List<Int>, data: List<Any>, padding: String, left: String, center: String, right: String): String {
        val formattedCells = (columnWidths zip data).map { (width, cell) ->
            formatCell(cell, width, padding)
        }
        return formattedCells.joinToString(center, left, right)
    }

    private fun formatRows(columnWidths: List<Int>, rows: List<List<Any>>): List<String> {
        val formatRow: (List<Any>) -> String = { a -> makeRow(columnWidths, a, " ", rowLeft, rowCenter, rowRight) }
        return rows.map { x -> formatRow(x) }
    }

    private fun formatCell(cell: Any?, width: Int, padding: String): String {
        return when (cell) {
            is LeftJustify -> JustifyUtil.leftJustify(cellToString(cell.x), width, padding)
            is RightJustify -> JustifyUtil.rightJustify(cellToString(cell.x), width, padding)
            null -> JustifyUtil.rightJustify(cellToString(cell), width, padding)
            is String -> JustifyUtil.leftJustify(cellToString(cell), width, padding)
            else -> JustifyUtil.rightJustify(cellToString(cell), width, padding)
        }
    }

    private fun <T> interleave(data: List<T>, separator: T): List<T> {
        fun combine(soFar: List<T>, next: T): List<T> {
            return listOf(next) + listOf(separator) + soFar
        }

        val combineLambda = { a: List<T>, b: T -> combine(a, b) }
        return if (data.isEmpty()) {
            emptyList()
        } else {
            data.drop(1).fold(listOf(data.first()), combineLambda).asReversed()
        }
    }

    private fun maxWidthForColumn(column: List<Any>): Int {
        return column.map { cell -> cellWidth(cell) }.max() ?: 0
    }

    private fun cellWidth(cell: Any?): Int {
        return cellToString(cell).length
    }

    private fun cellToString(cell: Any?): String {
        return when (cell) {
            null -> "null"
            is LeftJustify -> cellToString(cell.x)
            is RightJustify -> cellToString(cell.x)
            else -> cell.toString()
        }
    }
}

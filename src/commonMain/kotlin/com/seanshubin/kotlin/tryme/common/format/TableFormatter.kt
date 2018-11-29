package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.format.ListUtil.transpose

class TableFormatter(
    private val cellFormatter: CellFormatter,
    private val content: RowStyle,
    private val top: RowStyle? = null,
    private val bottom: RowStyle? = null,
    private val separator: RowStyle? = null
) {
    fun createTable(originalRows: List<List<Any?>>): List<String> {
        val paddedRows = makeAllRowsTheSameSize(originalRows, "")
        val columns = paddedRows.transpose()
        val columnWidths = columns.map { a: List<Any?> -> maxWidthForColumn(a) }
        val formattedRows = formatRows(columnWidths, paddedRows)
        val content = if (separator == null) {
            formattedRows
        } else {
            val content = separator.format(columnWidths)
            interleave(formattedRows, content)
        }
        val top = if (top == null) listOf() else listOf(top.format(columnWidths))
        val bottom = if (bottom == null) listOf() else listOf(bottom.format(columnWidths))
        return top + content + bottom
    }

    private fun makeAllRowsTheSameSize(rows: List<List<Any?>>, value: Any): List<List<Any?>> {
        val rowSizes = rows.map { row -> row.size }
        val targetSize = rowSizes.max() ?: 0

        fun makeRowSameSize(row: List<Any?>): List<Any?> {
            val extraCells = makeExtraCells(targetSize - row.size, value)
            return row + extraCells
        }

        return rows.map { makeRowSameSize(it) }
    }

    private fun makeExtraCells(howMany: Int, contents: Any): List<Any> {
        return (1..howMany).map { contents }
    }

    private fun formatRows(columnWidths: List<Int>, rows: List<List<Any?>>): List<String> =
        rows.map { row ->
            content.format(columnWidths, row, cellFormatter::formatCell)
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

    private fun maxWidthForColumn(column: List<Any?>): Int {
        return column.map { cell -> cellWidth(cell) }.max() ?: 0
    }

    private fun cellWidth(cell: Any?): Int {
        return cellFormatter.cellToString(cell).length
    }

    companion object {
        val boxDrawing = TableFormatter(
            cellFormatter = CellFormatter.default,
            content = RowStyle(
                left = "║",
                middle = " ",
                right = "║",
                separator = "│"
            ),
            top = RowStyle(
                left = "╔",
                middle = "═",
                right = "╗",
                separator = "╤"
            ),
            bottom = RowStyle(
                left = "╚",
                middle = "═",
                right = "╝",
                separator = "╧"
            ),
            separator = RowStyle(
                left = "╟",
                middle = "─",
                right = "╢",
                separator = "┼"
            )
        )
        val plainText = TableFormatter(
            cellFormatter = CellFormatter.default,
            content = RowStyle(
                left = "|",
                middle = " ",
                right = "|",
                separator = "|"
            ),
            top = RowStyle(
                left = "/",
                middle = "-",
                right = "\\",
                separator = "+"
            ),
            bottom = RowStyle(
                left = "\\",
                middle = "-",
                right = "/",
                separator = "+"
            ),
            separator = RowStyle(
                left = "+",
                middle = "-",
                right = "+",
                separator = "+"
            )
        )
        val minimal = TableFormatter(
            cellFormatter = CellFormatter.default,
            content = RowStyle(
                left = "",
                middle = " ",
                right = "",
                separator = " "
            ),
            top = null,
            bottom = null,
            separator = null
        )
    }
}

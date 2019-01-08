package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.format.StringUtil.escape
import com.seanshubin.kotlin.tryme.common.format.StringUtil.truncate

interface TableFormatter {
    interface Justify {
        data class Left(val x: Any?) : Justify

        data class Right(val x: Any?) : Justify
    }

    fun format(originalRows: List<List<Any?>>): List<String>

    companion object {
        val escapeString: (Any?) -> String = { cell ->
            when (cell) {
                null -> "null"
                else -> cell.toString().escape()
            }
        }

        fun escapeAndTruncateString(max: Int): (Any?) -> String = { cell ->
            escapeString(cell).truncate(max)
        }
    }
}

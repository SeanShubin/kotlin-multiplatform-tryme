package com.seanshubin.kotlin.tryme.common.format

interface CellFormatter {
    fun formatCell(cell: Any?, width: Int, padding: String): String
    fun cellToString(cell: Any?): String

    companion object {
        interface Justify {
            companion object {
                data class Left(val x: Any?) : Justify

                data class Right(val x: Any?) : Justify
            }
        }


        fun rightJustify(s: String, width: Int, padding: String = " "): String {
            return paddingFor(s, width, padding) + s
        }

        fun leftJustify(s: String, width: Int, padding: String = " "): String {
            return s + paddingFor(s, width, padding)
        }

        private fun paddingFor(s: String, width: Int, padding: String): String {
            val quantity = width - s.length
            return padding.repeat(quantity)
        }

        val default = object : CellFormatter {
            override fun formatCell(cell: Any?, width: Int, padding: String): String =
                when (cell) {
                    is Justify.Companion.Left -> leftJustify(cellToString(cell.x), width, padding)
                    is Justify.Companion.Right -> rightJustify(cellToString(cell.x), width, padding)
                    null -> rightJustify(cellToString(cell), width, padding)
                    is String -> leftJustify(cellToString(cell), width, padding)
                    else -> rightJustify(cellToString(cell), width, padding)
                }

            override fun cellToString(cell: Any?): String {
                return when (cell) {
                    null -> "null"
                    is Justify.Companion.Left -> cellToString(cell.x)
                    is Justify.Companion.Right -> cellToString(cell.x)
                    else -> cell.toString()
                }
            }
        }
    }
}

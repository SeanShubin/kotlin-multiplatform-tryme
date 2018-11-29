package com.seanshubin.kotlin.tryme.common.format

data class TableStyle(
    val top: RowStyle,
    val middle: RowStyle,
    val bottom: RowStyle,
    val separator: RowStyle?
)

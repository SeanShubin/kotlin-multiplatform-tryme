package com.seanshubin.kotlin.tryme.common.format

object TableStyles {
    val boxDrawing = TableStyle(
        top = RowStyle(
            left = "╔",
            middle = "═",
            right = "╗",
            separator = "╤"
        ),
        middle = RowStyle(
            left = "║",
            middle = " ",
            right = "║",
            separator = "│"
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
    val plainText = TableStyle(
        top = RowStyle(
            left = "/",
            middle = "-",
            right = "\\",
            separator = "+"
        ),
        middle = RowStyle(
            left = "|",
            middle = " ",
            right = "|",
            separator = "|"
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
    val minimal = TableStyle(
        top = RowStyle(
            left = "",
            middle = "",
            right = "",
            separator = ""
        ),
        middle = RowStyle(
            left = "",
            middle = " ",
            right = "",
            separator = " "
        ),
        bottom = RowStyle(
            left = "╚",
            middle = "═",
            right = "╝",
            separator = "╧"
        ),
        separator = null
    )
}

package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.format.TableFormatter.LeftJustify
import com.seanshubin.kotlin.tryme.common.format.TableFormatter.RightJustify
import kotlin.test.Test
import kotlin.test.assertEquals

class TableFormatterTest {
    @Test
    fun fancyTable() {
        val tableFormatter = TableFormatter.boxDrawing
        val input = listOf(
            listOf("Alice", "Bob", "Carol"),
            listOf("Dave", "Eve", "Mallory"),
            listOf("Peggy", "Trent", "Wendy")
        )
        val expected = listOf(
            "╔═════╤═════╤═══════╗",
            "║Alice│Bob  │Carol  ║",
            "╟─────┼─────┼───────╢",
            "║Dave │Eve  │Mallory║",
            "╟─────┼─────┼───────╢",
            "║Peggy│Trent│Wendy  ║",
            "╚═════╧═════╧═══════╝"
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(expected, actual)
    }

    @Test
    fun plainTextTable() {
        val tableFormatter = TableFormatter.plainText
        val input = listOf(
            listOf("Alice", "Bob", "Carol"),
            listOf("Dave", "Eve", "Mallory"),
            listOf("Peggy", "Trent", "Wendy")
        )
        val expected = listOf(
            "/-----+-----+-------\\",
            "|Alice|Bob  |Carol  |",
            "+-----+-----+-------+",
            "|Dave |Eve  |Mallory|",
            "+-----+-----+-------+",
            "|Peggy|Trent|Wendy  |",
            "\\-----+-----+-------/"
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(expected, actual)
    }

    @Test
    fun minimalTable() {
        val tableFormatter = TableFormatter.minimal
        val input = listOf(
            listOf("Alice", "Bob", "Carol"),
            listOf("Dave", "Eve", "Mallory"),
            listOf("Peggy", "Trent", "Wendy")
        )
        val expected = listOf(
            "Alice Bob   Carol  ",
            "Dave  Eve   Mallory",
            "Peggy Trent Wendy  "
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(expected, actual)
    }

    @Test
    fun leftAndRightJustify() {
        val tableFormatter = TableFormatter.boxDrawing
        val input = listOf(
            listOf("left justify column name", "default justification column name", "right justify column name"),
            listOf(LeftJustify("left"), "default", RightJustify("right")),
            listOf(LeftJustify(null), null, RightJustify(null)),
            listOf(LeftJustify(1), 1, RightJustify(1)),
            listOf(LeftJustify(2), 2, RightJustify(2)),
            listOf(LeftJustify(3), 3, RightJustify(3))
        )
        val expected = listOf(
            "╔════════════════════════╤═════════════════════════════════╤═════════════════════════╗",
            "║left justify column name│default justification column name│right justify column name║",
            "╟────────────────────────┼─────────────────────────────────┼─────────────────────────╢",
            "║left                    │default                          │                    right║",
            "╟────────────────────────┼─────────────────────────────────┼─────────────────────────╢",
            "║null                    │                             null│                     null║",
            "╟────────────────────────┼─────────────────────────────────┼─────────────────────────╢",
            "║1                       │                                1│                        1║",
            "╟────────────────────────┼─────────────────────────────────┼─────────────────────────╢",
            "║2                       │                                2│                        2║",
            "╟────────────────────────┼─────────────────────────────────┼─────────────────────────╢",
            "║3                       │                                3│                        3║",
            "╚════════════════════════╧═════════════════════════════════╧═════════════════════════╝"
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(actual, expected)
    }

    @Test
    fun leftAndRightJustifySomethingFar() {
        val tableFormatter = TableFormatter.boxDrawing
        assertEquals(tableFormatter.createTable(listOf(listOf("a"))), listOf("╔═╗", "║a║", "╚═╝"))
        assertEquals(
            tableFormatter.createTable(listOf(listOf(LeftJustify("a")))),
            listOf("╔═╗", "║a║", "╚═╝")
        )
        assertEquals(
            tableFormatter.createTable(listOf(listOf(RightJustify("a")))),
            listOf("╔═╗", "║a║", "╚═╝")
        )
    }

    @Test
    fun testNoColumns() {
        val tableFormatter = TableFormatter.boxDrawing
        assertEquals(tableFormatter.createTable(listOf(listOf())), listOf("╔╗", "║║", "╚╝"))
    }

    @Test
    fun testNoRows() {
        val tableFormatter = TableFormatter.boxDrawing
        assertEquals(tableFormatter.createTable(listOf()), listOf("╔╗", "╚╝"))
    }

    @Test
    fun replaceEmptyCellsWithBlankCells() {
        val tableFormat = TableFormatter.boxDrawing
        val input = listOf(
            listOf("Alice", "Bob", "Carol"),
            listOf("Dave", "Eve"),
            listOf("Peggy", "Trent", "Wendy")
        )
        val expected = listOf(
            "╔═════╤═════╤═════╗",
            "║Alice│Bob  │Carol║",
            "╟─────┼─────┼─────╢",
            "║Dave │Eve  │     ║",
            "╟─────┼─────┼─────╢",
            "║Peggy│Trent│Wendy║",
            "╚═════╧═════╧═════╝"
        )
        val actual = tableFormat.createTable(input)
        assertEquals(actual, expected)
    }
}

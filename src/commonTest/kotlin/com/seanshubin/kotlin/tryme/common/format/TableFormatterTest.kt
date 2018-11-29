package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.compare.ListDifference
import com.seanshubin.kotlin.tryme.common.format.CellFormatter.Companion.Justify.Companion.Left
import com.seanshubin.kotlin.tryme.common.format.CellFormatter.Companion.Justify.Companion.Right
import kotlin.test.Test
import kotlin.test.assertTrue

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
        assertLinesEqual(expected, actual)
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
        assertLinesEqual(expected, actual)
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
        actual.forEach { println(it) }
        assertLinesEqual(expected, actual)
    }

    @Test
    fun leftAndRightJustify() {
        val tableFormatter = TableFormatter.boxDrawing
        val input = listOf(
            listOf("left justify column name", "default justification column name", "right justify column name"),
            listOf(Left("left"), "default", Right("right")),
            listOf(Left(null), null, Right(null)),
            listOf(Left(1), 1, Right(1)),
            listOf(Left(2), 2, Right(2)),
            listOf(Left(3), 3, Right(3))
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
        assertLinesEqual(expected, actual)
    }

    @Test
    fun leftAndRightJustifySomethingFar() {
        val tableFormatter = TableFormatter.boxDrawing
        assertLinesEqual(tableFormatter.createTable(listOf(listOf("a"))), listOf("╔═╗", "║a║", "╚═╝"))
        assertLinesEqual(
            tableFormatter.createTable(listOf(listOf(Left("a")))),
            listOf("╔═╗", "║a║", "╚═╝")
        )
        assertLinesEqual(
            tableFormatter.createTable(listOf(listOf(Right("a")))),
            listOf("╔═╗", "║a║", "╚═╝")
        )
    }

    @Test
    fun testNoColumns() {
        val tableFormatter = TableFormatter.boxDrawing
        assertLinesEqual(tableFormatter.createTable(listOf(listOf())), listOf("╔╗", "║║", "╚╝"))
    }

    @Test
    fun testNoRows() {
        val tableFormatter = TableFormatter.boxDrawing
        assertLinesEqual(tableFormatter.createTable(listOf()), listOf("╔╗", "╚╝"))
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
        assertLinesEqual(expected, actual)
    }

    private fun assertLinesEqual(expected: List<String>, actual: List<String>) {
        val difference = ListDifference.compare(
            "expected", expected,
            "actual  ", actual
        )
        assertTrue(difference.isSame, difference.messageLines.joinToString("\n"))
    }
}

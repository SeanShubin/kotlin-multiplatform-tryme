package com.seanshubin.kotlin.tryme.common.schulze

import com.seanshubin.kotlin.tryme.common.schulze.Schulze.schulzeTally
import kotlin.test.Test
import kotlin.test.assertEquals

class SchulzeTest {
    @Test
    fun tallyWithTies() {
        // given
        val candidates = listOf("Alice", "Bob", "Carol", "Dave")
        val preferences = listOf(
            listOf(0, 1, 3, 2),
            listOf(4, 0, 2, 1),
            listOf(2, 3, 0, 4),
            listOf(3, 3, 1, 0)
        )
        val expected = listOf(
            Pair("1st", listOf("Bob", "Carol")),
            Pair("3rd", listOf("Alice", "Dave"))
        )

        // when
        val tallied = schulzeTally(candidates, preferences)

        // then
        assertEquals(expected, tallied)
    }
}
package com.seanshubin.kotlin.tryme.common.filter

import com.seanshubin.kotlin.tryme.common.filter.FilterFactory.createPartialSearch
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterFactoryTest {
    @Test
    fun partialSearch() {
        // given
        val list = listOf("Alice", "Bob", "Carol", "Dave", "Eve", "Mallory", "Trent", "Walter", "Peggy", "Victor")
        val expected = listOf("Carol", "Mallory")
        val partialSearch = createPartialSearch("ao")

        // when
        val actual = list.filter(partialSearch)

        // than
        assertEquals(expected, actual)
    }

    @Test
    fun partialSearchNotCaseSensitive() {
        // given
        val list = listOf("Alice", "Bob", "Carol", "Dave", "Eve", "Mallory", "Trent", "Walter", "Peggy", "Victor")
        val expected = listOf("Alice", "Dave", "Walter")
        val partialSearch = createPartialSearch("ae")

        // when
        val actual = list.filter(partialSearch)

        // than
        assertEquals(expected, actual)
    }

    @Test
    fun partialSearchAllowsAllIfEmpty() {
        // given
        val list = listOf("Alice", "Bob", "Carol", "Dave", "Eve", "Mallory", "Trent", "Walter", "Peggy", "Victor")
        val partialSearch = createPartialSearch("")

        // when
        val actual = list.filter(partialSearch)

        // than
        assertEquals(list, actual)
    }
}

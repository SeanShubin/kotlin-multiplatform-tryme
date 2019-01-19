package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.IndexedCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class EndTest {
    @Test
    fun checkMatchSuccess() {
        // given
        val endMatcher: Matcher<String> = End("end-rule")

        val iterator = listOf<String>().iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = Success("end-rule", Branch("end-rule", emptyList()), cursor)

        // when
        val actual = endMatcher.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkMatchFailure() {
        // given
        val endMatcher: Matcher<String> = End("end-rule")

        val iterator = listOf("foo").iterator()
        val cursor = IndexedCursor.create(iterator)

        // when
        val actual = endMatcher.checkMatch(cursor).toString()

        // then
        assertEquals("[1] End expected", actual)
    }
}

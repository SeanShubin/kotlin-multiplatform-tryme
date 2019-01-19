package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.IndexedCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueTest {
    @Test
    fun checkMatchSuccess() {
        // given
        val valueMatcher: Matcher<String> = Value("foo-rule", "foo")

        val iterator = listOf("foo").iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = Success("foo-rule", Leaf("foo-rule", "foo"), cursor.next())

        // when
        val actual = valueMatcher.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkMatchFailure() {
        // given
        val valueMatcher: Matcher<String> = Value("foo-rule", "foo")

        val iterator = listOf("bar").iterator()
        val cursor = IndexedCursor.create(iterator)

        // when
        val actual = valueMatcher.checkMatch(cursor).toString()

        // then
        assertEquals("[iterator] Expected 'foo'", actual)
    }
}

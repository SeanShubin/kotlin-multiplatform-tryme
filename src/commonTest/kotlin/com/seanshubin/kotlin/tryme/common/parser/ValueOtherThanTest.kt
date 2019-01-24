package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.IndexedCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueOtherThanTest {
    @Test
    fun checkMatchSuccess() {
        // given
        val valueMatcher: Matcher<String> = ValueOtherThan("not-foo-rule", "foo")

        val iterator = listOf("bar").iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = Success("not-foo-rule", Leaf("not-foo-rule", "bar"), cursor.next())

        // when
        val actual = valueMatcher.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkMatchFailure() {
        // given
        val valueMatcher: Matcher<String> = ValueOtherThan("not-foo-rule", "foo")

        val iterator = listOf("foo").iterator()
        val cursor = IndexedCursor.create(iterator)

        // when
        val actual = valueMatcher.checkMatch(cursor).toString()

        // then
        assertEquals("[1] Expected value other than 'foo', got 'foo'", actual)
    }

    @Test
    fun checkEnd() {
        // given
        val valueMatcher: Matcher<String> = ValueOtherThan("not-foo-rule", "foo")

        val iterator = listOf<String>().iterator()
        val cursor = IndexedCursor.create(iterator)

        // when
        val actual = valueMatcher.checkMatch(cursor).toString()

        // then
        assertEquals("[1] Expected value other than 'foo', got end of input", actual)
    }
}

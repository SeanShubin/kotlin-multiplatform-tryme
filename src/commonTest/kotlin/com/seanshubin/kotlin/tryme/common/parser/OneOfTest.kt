package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.IndexedCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class OneOfTest {
    @Test
    fun checkMatchFirst() {
        // given
        val name = "name"
        val fooMatcher: Matcher<String> = Value("foo-rule", "foo")
        val barMatcher: Matcher<String> = Value("bar-rule", "bar")
        val map = mapOf(Pair("foo", fooMatcher), Pair("bar", barMatcher))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val oneOf = OneOf(name, lookup, "foo", "bar")

        val iterator = listOf("foo").iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = Success("foo-rule", Leaf("foo-rule", "foo"), cursor.next())

        // when
        val actual = oneOf.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkMatchSecond() {
        // given
        val name = "name"
        val fooMatcher: Matcher<String> = Value("foo-rule", "foo")
        val barMatcher: Matcher<String> = Value("bar-rule", "bar")
        val map = mapOf(Pair("foo", fooMatcher), Pair("bar", barMatcher))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val oneOf = OneOf(name, lookup, "foo", "bar")

        val iterator = listOf("bar").iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = Success("bar-rule", Leaf("bar-rule", "bar"), cursor.next())

        // when
        val actual = oneOf.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkNoMatch() {
        // given
        val name = "name"
        val fooMatcher: Matcher<String> = Value("foo-rule", "foo")
        val barMatcher: Matcher<String> = Value("bar-rule", "bar")
        val map = mapOf(Pair("foo", fooMatcher), Pair("bar", barMatcher))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val oneOf = OneOf(name, lookup, "foo", "bar")

        val iterator = listOf("baz").iterator()
        val cursor = IndexedCursor.create(iterator)
        val expected = "[1] Expected one of: foo, bar"

        // when
        val actual = oneOf.checkMatch(cursor).toString()

        // then
        assertEquals(expected, actual)
    }
}

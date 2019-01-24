package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.IndexedCursor
import kotlin.test.Test
import kotlin.test.assertEquals

class SequenceTest {
    @Test
    fun checkMatchAll() {
        // given
        val name = "sequence-rule"
        val fooMatcher: Matcher<String> = Value("foo-rule", "foo")
        val barMatcher: Matcher<String> = Value("bar-rule", "bar")
        val map = mapOf(Pair("foo", fooMatcher), Pair("bar", barMatcher))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val sequence = Sequence(name, lookup, "foo", "bar")

        val iterator = listOf("foo", "bar").iterator()
        val cursor = IndexedCursor.create(iterator)
        val fooLeaf = Leaf("foo-rule", "foo")
        val barLeaf = Leaf("bar-rule", "bar")
        val leaves = listOf(fooLeaf, barLeaf)
        val expected = Success("sequence-rule", Branch("sequence-rule", leaves), cursor.next().next())

        // when
        val actual = sequence.checkMatch(cursor)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun checkMatchFailOnSecond() {
        // given
        val name = "sequence-rule"
        val fooMatcher: Matcher<String> = Value("foo-rule", "foo")
        val barMatcher: Matcher<String> = Value("bar-rule", "bar")
        val map = mapOf(Pair("foo", fooMatcher), Pair("bar", barMatcher))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val sequence = Sequence(name, lookup, "foo", "bar")

        val iterator = listOf("foo", "baz").iterator()
        val cursor = IndexedCursor.create(iterator)

        // when
        val actual = sequence.checkMatch(cursor).toString()

        // then
        assertEquals("[2] Expected 'bar'", actual)
    }
}

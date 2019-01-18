package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import kotlin.test.Test

class OneOfTest {
    @Test
    fun checkMatch() {
        // given
        val name = "name"
        val foo: Matcher<String> = object : Matcher<String> {
            override val name: String get() = TODO("not implemented")

            override fun checkMatch(cursor: RowColCursor<String>): Result<String> {
                TODO("not implemented")
            }
        }
        val bar: Matcher<String> = object : Matcher<String> {
            override val name: String get() = TODO("not implemented")

            override fun checkMatch(cursor: RowColCursor<String>): Result<String> {
                TODO("not implemented")
            }
        }
        val map = mapOf(Pair("foo", foo), Pair("bar", bar))

        val lookup: (String) -> Matcher<String> = { name ->
            map[name]!!
        }
        val oneOf = OneOf(name, lookup, "foo", "bar")
    }
}
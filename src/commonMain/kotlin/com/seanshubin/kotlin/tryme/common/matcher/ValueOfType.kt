package com.seanshubin.kotlin.tryme.common.matcher

import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor
import kotlin.reflect.KClass

class ValueOfType<T : Any, U : T>(override val name: String, private val expected: KClass<U>) : Matcher<T> {
    override fun checkMatch(cursor: RowColCursor<T>): Result<T> {
        return if (cursor.value::class == expected) {
            Success(name, Leaf(cursor.value), cursor.next())
        } else {
            Failure("Expected '$expected'", cursor)
        }
    }
}

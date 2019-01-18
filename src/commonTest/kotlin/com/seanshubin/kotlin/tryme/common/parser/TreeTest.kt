package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.TestUtil.assertMultilineEquals
import com.seanshubin.kotlin.tryme.common.format.StringUtil.toLines
import kotlin.test.Test
import kotlin.test.assertEquals

class TreeTest {
    @Test
    fun leafValues() {
        // given
        val leaf = Leaf("name", "value")

        // when
        val actual = leaf.values()

        // then
        assertEquals(listOf("value"), actual)
    }

    @Test
    fun branchValues() {
        // given
        val branch = Branch(
            "branch", listOf(
                Leaf("leaf-a", "aaa"),
                Leaf("leaf-b", "bbb"),
                Leaf("leaf-c", "ccc")
            )
        )

        // when
        val actual = branch.values()

        // then
        assertEquals(listOf("aaa", "bbb", "ccc"), actual)
    }

    @Test
    fun treeValues() {
        // given
        val branch = Branch(
            "branch-1", listOf(
                Leaf("leaf-a", "aaa"),
                Branch(
                    "branch-1", listOf(
                        Leaf("leaf-b", "bbb"),
                        Leaf("leaf-c", "ccc"),
                        Leaf("leaf-d", "ddd"),
                        Leaf("leaf-e", "eee")
                    )
                )
            )
        )

        // when
        val actual = branch.values()

        // then
        assertEquals(listOf("aaa", "bbb", "ccc", "ddd", "eee"), actual)
    }

    @Test
    fun treeLines() {
        // given
        val branch = Branch(
            "branch-1", listOf(
                Leaf("leaf-a", "aaa"),
                Branch(
                    "branch-2", listOf(
                        Leaf("leaf-b", "bbb"),
                        Leaf("leaf-c", "ccc"),
                        Leaf("leaf-d", "ddd"),
                        Leaf("leaf-e", "eee")
                    )
                )
            )
        )
        val expected =
            """branch-1
              |  leaf-a
              |    aaa
              |  branch-2
              |    leaf-b
              |      bbb
              |    leaf-c
              |      ccc
              |    leaf-d
              |      ddd
              |    leaf-e
              |      eee""".trimMargin().toLines()

        // when
        val actual = branch.toLines()

        // then
        assertMultilineEquals(expected, actual)
    }
}
package com.seanshubin.kotlin.tryme.common.format

import com.seanshubin.kotlin.tryme.common.format.StringUtil.escape
import com.seanshubin.kotlin.tryme.common.format.StringUtil.toLines
import com.seanshubin.kotlin.tryme.common.format.StringUtil.truncate
import com.seanshubin.kotlin.tryme.common.format.StringUtil.unescape
import com.seanshubin.kotlin.tryme.common.format.StringUtil.clean
import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilTest {
    @Test
    fun escape() {
        assertEquals("""blah\nblah""", "blah\nblah".escape())
        assertEquals("""blah\bblah""", "blah\bblah".escape())
        assertEquals("""blah\tblah""", "blah\tblah".escape())
        assertEquals("""blah\rblah""", "blah\rblah".escape())
        assertEquals("""blah\"blah""", "blah\"blah".escape())
        assertEquals("""blah\'blah""", "blah\'blah".escape())
        assertEquals("""blah\\blah""", "blah\\blah".escape())
    }

    @Test
    fun unescape() {
        assertEquals("blah\nblah", """blah\nblah""".unescape())
        assertEquals("blah\bblah", """blah\bblah""".unescape())
        assertEquals("blah\tblah", """blah\tblah""".unescape())
        assertEquals("blah\rblah", """blah\rblah""".unescape())
        assertEquals("blah\"blah", """blah\"blah""".unescape())
        assertEquals("blah\'blah", """blah\'blah""".unescape())
        assertEquals("blah\\blah", """blah\\blah""".unescape())
    }

    @Test
    fun toLines() {
        assertEquals(listOf("a", "b", "c", "d"), "a\nb\r\nc\rd".toLines())
    }

    @Test
    fun truncate() {
        assertEquals("aaaaa", "a".repeat(5).truncate(10))
        assertEquals("aaaaaaaaaa", "a".repeat(10).truncate(10))
        assertEquals("<100 characters, showing first 10> aaaaaaaaaa", "a".repeat(100).truncate(10))
    }

    @Test
    fun hex() {
        // given
        val byteArray: ByteArray = listOf(0, 1, 126, 127, 128, 255).map { it.toByte() }.toByteArray()
        val expected = "00017E7F80FF"

        // when
        val actual = StringUtil.hex(byteArray)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun clean(){
        assertEquals("foo bar", "  foo  bar  ".clean())
    }

    @Test
    fun cleanList(){
        // given
        val list = listOf("", "  foo  bar  ", "", "biz\tbaz", "")
        val expected = listOf("foo bar", "biz baz")

        // when
        val actual = list.clean()

        // then
        assertEquals(expected, actual)
    }
}

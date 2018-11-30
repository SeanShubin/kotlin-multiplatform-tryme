package com.seanshubin.kotlin.tryme.jvm.io

import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.bytesToOutputStream
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.bytesToString
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.inputStreamToString
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.readerToString
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.stringToBytes
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.stringToInputStream
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.stringToOutputStream
import com.seanshubin.kotlin.tryme.jvm.io.IoUtil.stringToReader
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

class IoUtilTest {
    private val charset = StandardCharsets.UTF_8

    @Test
    fun testBytes() {
        val inputStream = stringToInputStream("Hello, world!", charset)
        val string = inputStreamToString(inputStream, charset)
        assertEquals("Hello, world!", string)
    }

    @Test
    fun testStringToOutputStream() {
        val original = "Hello, world!"
        val outputStream = ByteArrayOutputStream()
        stringToOutputStream(original, charset, outputStream)
        val string = bytesToString(outputStream.toByteArray(), charset)
        assertEquals("Hello, world!", string)
    }

    @Test
    fun testBytesToOutputStream() {
        val original = "Hello, world!"
        val bytes = stringToBytes(original, charset)
        val outputStream = ByteArrayOutputStream()
        bytesToOutputStream(bytes, outputStream)
        val string = bytesToString(outputStream.toByteArray(), charset)
        assertEquals("Hello, world!", string)
    }

    @Test
    fun testChars() {
        val reader = stringToReader("Hello, world!")
        val string = readerToString(reader)
        assertEquals("Hello, world!", string)
    }
}

package com.seanshubin.kotlin.tryme.jvm.http

import org.junit.Test
import kotlin.test.assertEquals

class CookieTest {
    @Test
    fun sample1() {
        // given
        val text = "LSID=DQAAAK…Eaem_vYg; Path=/accounts; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly"

        // when
        val cookie = Cookie.parse(text)

        // then
        assertEquals("LSID", cookie.name)
        assertEquals("DQAAAK…Eaem_vYg", cookie.value)
        assertEquals("Path=/accounts; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly", cookie.attributes)
    }
}
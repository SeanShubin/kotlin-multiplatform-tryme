package com.seanshubin.kotlin.tryme.jvm.http

import org.junit.Test
import kotlin.test.assertEquals

class CookieTest{
    @Test
    fun sample1(){
        // given
        val text = "LSID=DQAAAK…Eaem_vYg; Path=/accounts; Expires=Wed, 13 Jan 2021 22:23:01 GMT; Secure; HttpOnly"

        // when
        val cookie = Cookie.parse(text)

        // then
        assertEquals("LSID", cookie.name)
        assertEquals("DQAAAK…Eaem_vYg", cookie.value)
        assertEquals("/accounts", cookie.attributes["Path"])
        assertEquals("Wed, 13 Jan 2021 22:23:01 GMT", cookie.attributes["Expires"])
        assertEquals(true, cookie.attributes.contains("Secure"))
        assertEquals(true, cookie.attributes.contains("HttpOnly"))
    }

    @Test
    fun sample2(){
        // given
        val text = "aaa=5aa5ee35-6c4a-4317-ae1c-8d5d0b2d923f; bbb=.ccc.com; path=/,ddd=151604753396005259\$0\$1; domain=.eee.com; path=/; expires=Mon, 03-Feb-2020 01:56:03 GMT,fff=QQWg0CTqA48q; domain=.ggg.com; path=/; expires=Mon, 03-Feb-2020 01:56:03 GMT"

        // when
        val cookie = Cookie.parse(text)

        // then
        assertEquals("LSID", cookie.name)
        assertEquals("DQAAAK…Eaem_vYg", cookie.value)
        assertEquals("/accounts", cookie.attributes["Path"])
        assertEquals("Wed, 13 Jan 2021 22:23:01 GMT", cookie.attributes["Expires"])
        assertEquals(true, cookie.attributes.contains("Secure"))
        assertEquals(true, cookie.attributes.contains("HttpOnly"))
    }

}
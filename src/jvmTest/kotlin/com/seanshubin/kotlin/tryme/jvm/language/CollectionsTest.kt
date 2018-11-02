package com.seanshubin.kotlin.tryme.jvm.language

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class CollectionsTest{
    @Test
    fun sortCollection(){
        val list = listOf(2,1,3)
        Collections.sort(list)
        assertEquals(listOf(1,2,3), list)
    }
}
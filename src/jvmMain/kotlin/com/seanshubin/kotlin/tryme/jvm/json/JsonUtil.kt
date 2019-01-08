package com.seanshubin.kotlin.tryme.jvm.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object JsonUtil {
    val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().enable(SerializationFeature.INDENT_OUTPUT)

    fun String.normalizeJson(): String {
        val asObject = mapper.readValue<Any>(this)
        val asNormalized = mapper.writeValueAsString(asObject)
        return asNormalized
    }
}

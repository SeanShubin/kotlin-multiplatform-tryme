package com.seanshubin.kotlin.tryme.common.parser

class MatchException(result: Failure<*>) : RuntimeException("${result.errorAtPosition.summary} ${result.message}")

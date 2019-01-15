package com.seanshubin.kotlin.tryme.common.matcher

class MatchException(result: Failure<*>) : RuntimeException("${result.errorAtPosition.detail} ${result.message}")

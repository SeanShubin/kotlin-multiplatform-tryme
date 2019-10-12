package com.seanshubin.kotlin.tryme.jvm.characters

import kotlin.streams.toList

fun List<Int>.codePointsToString(): String = String(toIntArray(), 0, size)

fun String.toCodePointList(): List<Int> = this.codePoints().toList()

fun String.codePointCount(): Int = codePointCount(0, length)

fun main() {
    val aCodePoint = 97
    val gClefCodePoint = 0x0001D11E
    val bCodePoint = 98
    val originalCodePoints = listOf(aCodePoint, gClefCodePoint, bCodePoint)
    val s = originalCodePoints.codePointsToString()
    val codePoints = s.toCodePointList()
    println("s = $s")
    println("s.length = ${s.length}")
    println("s.codePointCount() = ${s.codePointCount()}")
    println("codePoints = $codePoints")
}

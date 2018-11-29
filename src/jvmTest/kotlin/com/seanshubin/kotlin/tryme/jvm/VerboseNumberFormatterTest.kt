package com.seanshubin.kotlin.tryme.jvm

import org.junit.Test
import java.math.BigInteger
import kotlin.test.assertEquals

class VerboseNumberFormatterTest {
    @Test
    fun verboseNumberFormatter() {
        verify(0, "Zero")
        verify(1, "One")
        verify(19, "Nineteen")
        verify(-19, "Negative Nineteen")
        verify(20, "Twenty")
        verify(99, "Ninety Nine")
        verify(100, "One Hundred")
        verify(101, "One Hundred One")
        verify(999, "Nine Hundred Ninety Nine")
        verify(1000, "One Thousand")
        verify(1001, "One Thousand One")
        verify(999999, "Nine Hundred Ninety Nine Thousand Nine Hundred Ninety Nine")
        verify(1000000, "One Million")
        verify(1000001, "One Million One")
        verify(999999999, "Nine Hundred Ninety Nine Million Nine Hundred Ninety Nine Thousand Nine Hundred Ninety Nine")
        verify(1000000000, "One Billion")
        verify(1000000001, "One Billion One")
        verify(
            Int.MAX_VALUE.toLong(),
            "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven"
        )
        verify(
            Long.MAX_VALUE,
            "Nine Quintillion Two Hundred Twenty Three Quadrillion Three Hundred Seventy Two Trillion Thirty Six Billion Eight Hundred Fifty Four Million Seven Hundred Seventy Five Thousand Eight Hundred Seven"
        )
        verify("1000000000002000000000000003", "One Octillion Two Quadrillion Three")
        verify(
            "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456",
            "One Hundred Twenty Three Quadragintillion Four Hundred Fifty Six Novemtrigintillion Seven Hundred Eighty Nine Octotrigintillion Twelve Septentrigintillion Three Hundred Forty Five Sestrigintillion Six Hundred Seventy Eight Quinquatrigintillion Nine Hundred One Quattuortrigintillion Two Hundred Thirty Four Trestrigintillion Five Hundred Sixty Seven Duotrigintillion Eight Hundred Ninety Untrigintillion One Hundred Twenty Three Trigintillion Four Hundred Fifty Six Novemvigintillion Seven Hundred Eighty Nine Octovigintillion Twelve Septemvigintillion Three Hundred Forty Five Sesvigintillion Six Hundred Seventy Eight Quinquavigintillion Nine Hundred One Quattuorvigintillion Two Hundred Thirty Four Tresvigintillion Five Hundred Sixty Seven Duovigintillion Eight Hundred Ninety Unvigintillion One Hundred Twenty Three Vigintillion Four Hundred Fifty Six Novemdecillion Seven Hundred Eighty Nine Octodecillion Twelve Septendecillion Three Hundred Forty Five Sexdecillion Six Hundred Seventy Eight Quindecillion Nine Hundred One Quattuordecillion Two Hundred Thirty Four Tredecillion Five Hundred Sixty Seven Duodecillion Eight Hundred Ninety Undecillion One Hundred Twenty Three Decillion Four Hundred Fifty Six Nonillion Seven Hundred Eighty Nine Octillion Twelve Septillion Three Hundred Forty Five Sextillion Six Hundred Seventy Eight Quintillion Nine Hundred One Quadrillion Two Hundred Thirty Four Trillion Five Hundred Sixty Seven Billion Eight Hundred Ninety Million One Hundred Twenty Three Thousand Four Hundred Fifty Six"
        )
        verify(
            "999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "Nine Hundred Ninety Nine Quadragintillion Nine Hundred Ninety Nine Novemtrigintillion Nine Hundred Ninety Nine Octotrigintillion Nine Hundred Ninety Nine Septentrigintillion Nine Hundred Ninety Nine Sestrigintillion Nine Hundred Ninety Nine Quinquatrigintillion Nine Hundred Ninety Nine Quattuortrigintillion Nine Hundred Ninety Nine Trestrigintillion Nine Hundred Ninety Nine Duotrigintillion Nine Hundred Ninety Nine Untrigintillion Nine Hundred Ninety Nine Trigintillion Nine Hundred Ninety Nine Novemvigintillion Nine Hundred Ninety Nine Octovigintillion Nine Hundred Ninety Nine Septemvigintillion Nine Hundred Ninety Nine Sesvigintillion Nine Hundred Ninety Nine Quinquavigintillion Nine Hundred Ninety Nine Quattuorvigintillion Nine Hundred Ninety Nine Tresvigintillion Nine Hundred Ninety Nine Duovigintillion Nine Hundred Ninety Nine Unvigintillion Nine Hundred Ninety Nine Vigintillion Nine Hundred Ninety Nine Novemdecillion Nine Hundred Ninety Nine Octodecillion Nine Hundred Ninety Nine Septendecillion Nine Hundred Ninety Nine Sexdecillion Nine Hundred Ninety Nine Quindecillion Nine Hundred Ninety Nine Quattuordecillion Nine Hundred Ninety Nine Tredecillion Nine Hundred Ninety Nine Duodecillion Nine Hundred Ninety Nine Undecillion Nine Hundred Ninety Nine Decillion Nine Hundred Ninety Nine Nonillion Nine Hundred Ninety Nine Octillion Nine Hundred Ninety Nine Septillion Nine Hundred Ninety Nine Sextillion Nine Hundred Ninety Nine Quintillion Nine Hundred Ninety Nine Quadrillion Nine Hundred Ninety Nine Trillion Nine Hundred Ninety Nine Billion Nine Hundred Ninety Nine Million Nine Hundred Ninety Nine Thousand Nine Hundred Ninety Nine"
        )
        verify(
            "-999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "Negative Nine Hundred Ninety Nine Quadragintillion Nine Hundred Ninety Nine Novemtrigintillion Nine Hundred Ninety Nine Octotrigintillion Nine Hundred Ninety Nine Septentrigintillion Nine Hundred Ninety Nine Sestrigintillion Nine Hundred Ninety Nine Quinquatrigintillion Nine Hundred Ninety Nine Quattuortrigintillion Nine Hundred Ninety Nine Trestrigintillion Nine Hundred Ninety Nine Duotrigintillion Nine Hundred Ninety Nine Untrigintillion Nine Hundred Ninety Nine Trigintillion Nine Hundred Ninety Nine Novemvigintillion Nine Hundred Ninety Nine Octovigintillion Nine Hundred Ninety Nine Septemvigintillion Nine Hundred Ninety Nine Sesvigintillion Nine Hundred Ninety Nine Quinquavigintillion Nine Hundred Ninety Nine Quattuorvigintillion Nine Hundred Ninety Nine Tresvigintillion Nine Hundred Ninety Nine Duovigintillion Nine Hundred Ninety Nine Unvigintillion Nine Hundred Ninety Nine Vigintillion Nine Hundred Ninety Nine Novemdecillion Nine Hundred Ninety Nine Octodecillion Nine Hundred Ninety Nine Septendecillion Nine Hundred Ninety Nine Sexdecillion Nine Hundred Ninety Nine Quindecillion Nine Hundred Ninety Nine Quattuordecillion Nine Hundred Ninety Nine Tredecillion Nine Hundred Ninety Nine Duodecillion Nine Hundred Ninety Nine Undecillion Nine Hundred Ninety Nine Decillion Nine Hundred Ninety Nine Nonillion Nine Hundred Ninety Nine Octillion Nine Hundred Ninety Nine Septillion Nine Hundred Ninety Nine Sextillion Nine Hundred Ninety Nine Quintillion Nine Hundred Ninety Nine Quadrillion Nine Hundred Ninety Nine Trillion Nine Hundred Ninety Nine Billion Nine Hundred Ninety Nine Million Nine Hundred Ninety Nine Thousand Nine Hundred Ninety Nine"
        )
    }

    fun verify(input: Long, expected: String) {
        val actual = VerboseNumberFormatter.format(BigInteger.valueOf(input))
        assertEquals(expected, actual)
    }

    fun verify(input: String, expected: String) {
        val actual = VerboseNumberFormatter.format(BigInteger(input))
        assertEquals(expected, actual)
    }
}

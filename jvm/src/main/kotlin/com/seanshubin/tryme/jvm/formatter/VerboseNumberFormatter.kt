package com.seanshubin.tryme.jvm.formatter

import java.math.BigInteger

object VerboseNumberFormatter {
    fun format(x: BigInteger): String {
        return formatNumberToList(x).joinToString(" ")
    }

    private val ZERO: BigInteger = BigInteger.ZERO
    private val TEN: BigInteger = BigInteger.valueOf(10)
    private val TWENTY: BigInteger = BigInteger.valueOf(20)
    private val HUNDRED: BigInteger = BigInteger.valueOf(100)
    private val THOUSAND: BigInteger = BigInteger.valueOf(1000)
    private val ONES = listOf(
            "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen")
    private val TENS = listOf(
            "Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")
    private val SCALE = listOf(
            "Thousand", "Million", "Billion", "Trillion", "Quadrillion", "Quintillion", "Sextillion", "Septillion",
            "Octillion", "Nonillion", "Decillion", "Undecillion", "Duodecillion", "Tredecillion", "Quattuordecillion",
            "Quindecillion", "Sexdecillion", "Septendecillion", "Octodecillion", "Novemdecillion", "Vigintillion",
            "Unvigintillion", "Duovigintillion", "Tresvigintillion", "Quattuorvigintillion", "Quinquavigintillion",
            "Sesvigintillion", "Septemvigintillion", "Octovigintillion", "Novemvigintillion", "Trigintillion",
            "Untrigintillion", "Duotrigintillion", "Trestrigintillion", "Quattuortrigintillion", "Quinquatrigintillion",
            "Sestrigintillion", "Septentrigintillion", "Octotrigintillion", "Novemtrigintillion", "Quadragintillion")

    private fun formatNumberToList(x: BigInteger): List<String> {
        when {
            x < BigInteger.ZERO -> return listOf("Negative") + formatNumberToList(-x)
            x < TWENTY -> return listOf(ONES[x.intValueExact()])
            x < HUNDRED -> {
                val (tens, ones) = x divMod TEN
                return if (ones == ZERO)
                    listOf(TENS[tens.intValueExact()])
                else
                    listOf(TENS[tens.intValueExact()]) + formatNumberToList(ones)
            }
            x < THOUSAND -> {
                val (hundreds, ones) = x divMod HUNDRED
                return if (ones == ZERO)
                    listOf(ONES[hundreds.intValueExact()], "Hundred")
                else
                    listOf(ONES[hundreds.intValueExact()], "Hundred") + formatNumberToList(ones)
            }
            else -> {
                fun scaledNumber(scale: Int, number: BigInteger): List<String> {
                    val (big, small) = number divMod THOUSAND
                    val verboseBig = if (big == ZERO) emptyList() else scaledNumber(scale + 1, big)
                    val verboseSmall = if (small == ZERO) emptyList() else formatNumberToList(small) + listOf(SCALE[scale])
                    return verboseBig + verboseSmall
                }
                val (thousands, ones) = x divMod THOUSAND
                val verboseBig = scaledNumber(0, thousands)
                val verboseSmall = if (ones == ZERO) emptyList() else formatNumberToList(ones)
                return verboseBig + verboseSmall
            }
        }
    }

    private infix fun BigInteger.divMod(denominator: BigInteger): Pair<BigInteger, BigInteger> {
        val results = this.divideAndRemainder(denominator)
        return Pair(results[0], results[1])
    }
}

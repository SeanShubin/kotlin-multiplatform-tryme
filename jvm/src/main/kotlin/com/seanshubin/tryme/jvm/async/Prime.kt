package com.seanshubin.tryme.jvm.async

import java.math.BigInteger

object Prime {
    private val ZERO = BigInteger.ZERO
    private val ONE = BigInteger.ONE
    private val TWO = BigInteger.valueOf(2)
    private val THREE = BigInteger.valueOf(3)
    fun nthPrime(index: BigInteger): BigInteger =
            when (index) {
                ZERO -> ONE
                ONE -> TWO
                else -> nextPrime(listOf(TWO), THREE, index - TWO)
            }

    private tailrec fun nextPrime(soFarSeq: List<BigInteger>, candidate: BigInteger, iterations: BigInteger): BigInteger {
        fun isDivisible(soFar: BigInteger): Boolean = candidate.mod(soFar) == ZERO

        return if (soFarSeq.any(::isDivisible)) {
            nextPrime(soFarSeq, candidate + TWO, iterations)
        } else {
            if (iterations == ZERO) {
                candidate
            } else {
                nextPrime(soFarSeq + candidate, candidate + TWO, iterations - ONE)
            }
        }
    }
}

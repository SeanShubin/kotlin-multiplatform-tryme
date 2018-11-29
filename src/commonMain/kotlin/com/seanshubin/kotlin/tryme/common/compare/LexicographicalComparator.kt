package com.seanshubin.kotlin.tryme.common.compare

import com.seanshubin.kotlin.tryme.common.compare.LexicographicalComparatorUtil.isDigit
import com.seanshubin.kotlin.tryme.common.compare.LexicographicalComparatorUtil.splitNumbers

object LexicographicalComparator : Comparator<String> {
    override fun compare(a: String, b: String): Int =
        if (a == b) {
            0
        } else {
            compareLists(splitNumbers(a), splitNumbers(b))
        }

    private fun compareLists(leftList: List<String>, rightList: List<String>): Int {
        if (leftList.isEmpty()) {
            return if (rightList.isEmpty()) {
                0
            } else {
                -1
            }
        } else {
            return if (rightList.isEmpty()) {
                1
            } else {
                val left = leftList[0]
                val right = rightList[0]
                val partComparison = compareParts(left, right)
                if (partComparison == 0) {
                    compareLists(leftList.drop(1), rightList.drop(1))
                } else {
                    partComparison
                }
            }
        }
    }

    private fun compareParts(left: String, right: String): Int =
        if (isDigit(left[0])) {
            if (isDigit(right[0])) {
                compareNumbers(left, right)
            } else {
                -1
            }
        } else {
            if (isDigit(right[0])) {
                1
            } else {
                left.compareTo(right)
            }
        }

    private fun compareNumbers(left: String, right: String): Int {
        val maxSize = maxOf(left.length, right.length)
        val paddedLeft = pad(left, maxSize)
        val paddedRight = pad(right, maxSize)
        val paddedCompare = paddedLeft.compareTo(paddedRight)
        if (paddedCompare == 0) {
            return left.length.compareTo(right.length)
        } else {
            return paddedCompare
        }
    }

    private fun pad(s: String, size: Int): String {
        val numZeroes = size - s.length
        val zeroes = "0".repeat(numZeroes)
        return zeroes + s
    }
}

package com.seanshubin.kotlin.tryme.common.filter

object FilterFactory {
    fun createPartialSearch(partial: String): (String) -> Boolean {
        fun partialSearch(target: String): Boolean {
            var partialIndex = 0
            var targetIndex = 0
            while (partialIndex < partial.length && targetIndex < target.length) {
                if (partial[partialIndex].equals(target[targetIndex], ignoreCase = true)) {
                    partialIndex++
                }
                targetIndex++
            }
            return partialIndex == partial.length
        }
        return ::partialSearch
    }
}

package com.seanshubin.kotlin.tryme.jvm.compare

import com.seanshubin.kotlin.tryme.common.LexicographicalComparator

object JvmLexicographicalComparator : Comparator<String> {
    override fun compare(left: String?, right: String?): Int {
        if (left == right) return 0
        if (left == null) return -1
        if (right == null) return 1
        return LexicographicalComparator.compare(left, right)
    }
}

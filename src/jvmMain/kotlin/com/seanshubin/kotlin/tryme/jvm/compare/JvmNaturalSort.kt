package com.seanshubin.kotlin.tryme.jvm.compare

import com.seanshubin.kotlin.tryme.common.compare.NaturalSort

object JvmNaturalSort : Comparator<String> {
    override fun compare(left: String?, right: String?): Int {
        if (left == right) return 0
        if (left == null) return -1
        if (right == null) return 1
        return NaturalSort.compare(left, right)
    }
}

package com.seanshubin.kotlin.tryme.common.schulze

import kotlin.math.max
import kotlin.math.min

object Schulze {
    fun schulzeTally(candidates: List<String>, rows: List<List<Int>>): List<Pair<String, List<String>>> {
        val strongestPaths = strongestPaths(rows)
        val tallied = tally(strongestPaths, emptyList(), emptyList())
        val result = mutableListOf<Pair<String, List<String>>>()
        var place = 1
        tallied.forEach { talliedRow ->
            val candidatesAtPlace = talliedRow.map { candidates[it] }
            val placeString = placeString(place)
            result.add(Pair(placeString, candidatesAtPlace))
            place += talliedRow.size
        }
        return result
    }

    fun strongestPaths(rows: List<List<Int>>): List<List<Int>> {
        val size = rows.size
        val strongestPaths = mutableList2(size, size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                strongestPaths[i][j] = rows[i][j]
            }
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i != j) {
                    for (k in 0 until size) {
                        if (i != k && j != k) {
                            strongestPaths[j][k] =
                                max(strongestPaths[j][k], min(strongestPaths[j][i], strongestPaths[i][k]))
                        }
                    }
                }
            }
        }
        return strongestPaths
    }

    tailrec fun tally(rows: List<List<Int>>, soFar: List<List<Int>>, indices: List<Int>): List<List<Int>> {
        val size = rows.size
        return if (indices.size == size) soFar
        else {
            val undefeated = (0 until size).filter { i ->
                !indices.contains(i) && (0 until size).all { j ->
                    indices.contains(j) || rows[i][j] >= rows[j][i]
                }
            }
            tally(rows, soFar + listOf(undefeated), indices + undefeated)
        }
    }

    private fun mutableList2(rowCount: Int, colCount: Int): MutableList<MutableList<Int>> =
        mutableListOf(*(0 until rowCount).map {
            mutableListOf(*(0 until colCount).map {
                0
            }.toTypedArray())
        }.toTypedArray())

    private fun placeString(place: Int): String = when (place) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${place}th"
    }
}

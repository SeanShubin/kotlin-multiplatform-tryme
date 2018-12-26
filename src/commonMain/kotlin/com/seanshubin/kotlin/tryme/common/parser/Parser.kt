package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.DetailCursor
import com.seanshubin.kotlin.tryme.common.cursor.RowCol
import com.seanshubin.kotlin.tryme.common.cursor.RowColCursor

class Parser(vararg rules: Rule) {
    private val ruleMap: Map<String, Rule> = rules.map { Pair(it.name, it) }.toMap()

    fun parse(ruleName: String, s: String): Result {
        val cursor = RowColCursor.create(s)
        val rule = ruleMap[ruleName]!!
        return parse(rule, cursor)
    }

    fun parse(rule: Rule, cursor: DetailCursor<Char, RowCol>): Result {
        val result = rule.apply(Result(ruleMap = ruleMap, cursor = cursor))
        return result
    }
}
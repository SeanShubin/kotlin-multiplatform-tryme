package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor
import com.seanshubin.kotlin.tryme.common.cursor.StringCursor

class Parser(vararg rules:Rule){
    private val ruleMap:Map<String, Rule> = rules.map { Pair(it.name, it) }.toMap()

    fun parse(ruleName:String, s:String):Result{
        val cursor = StringCursor(s)
        val rule = ruleMap[ruleName]!!
        return parse(rule, cursor)
    }
    fun parse(rule:Rule, cursor: Cursor<Char, Int>):Result {
        val result = rule.apply(Result(ruleMap = ruleMap, cursor = cursor))
        return result
    }
}
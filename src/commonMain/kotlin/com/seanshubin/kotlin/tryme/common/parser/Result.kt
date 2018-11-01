package com.seanshubin.kotlin.tryme.common.parser

import com.seanshubin.kotlin.tryme.common.cursor.Cursor

data class Result(val ruleMap:Map<String, Rule>,
                  val cursor:Cursor<Char, Int>,
                  val errorMessage:String? = null,
                  val trees:List<Tree> = listOf()) {
    val isError:Boolean get() = !isSuccess
    val isSuccess:Boolean get() = errorMessage.isNullOrEmpty()
}

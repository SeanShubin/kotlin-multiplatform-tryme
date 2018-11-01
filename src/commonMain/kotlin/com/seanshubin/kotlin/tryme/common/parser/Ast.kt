package com.seanshubin.kotlin.tryme.common.parser

interface Ast{
    val value:Int
}

data class Num(override val value:Int):Ast
data class Plus(val left:Ast, val right:Ast):Ast{
    override val value: Int
        get() = left.value + right.value
}
data class Minus(val left:Ast, val right:Ast):Ast{
    override val value: Int
        get() = left.value - right.value
}
data class Times(val left:Ast, val right:Ast):Ast{
    override val value: Int
        get() = left.value * right.value
}
data class Divide(val left:Ast, val right:Ast):Ast{
    override val value: Int
        get() = left.value / right.value
}

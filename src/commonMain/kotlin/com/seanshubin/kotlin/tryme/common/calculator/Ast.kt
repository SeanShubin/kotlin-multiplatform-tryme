package com.seanshubin.kotlin.tryme.common.calculator

interface Ast {
    val value: Int

    companion object {
        data class Mul(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        data class Div(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        data class Add(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        data class Sub(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        data class Num(override val value: Int) : Ast
    }
}

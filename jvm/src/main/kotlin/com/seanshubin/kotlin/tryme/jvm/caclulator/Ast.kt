package com.seanshubin.kotlin.tryme.jvm.caclulator

interface Ast {
    val value: Int

    companion object {
        class Mul(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        class Div(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        class Add(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        class Sub(val left: Ast, val right: Ast) : Ast {
            override val value: Int get() = left.value + right.value
        }

        class Num(override val value: Int) : Ast
    }
}

package com.seanshubin.kotlin.tryme.common.calculator

interface Expression {
    fun eval(): Int
    data class Number(val value: Int) : Expression {
        override fun eval(): Int = value
    }

    data class Plus(val left: Expression, val right: Expression) : Expression {
        override fun eval(): Int = left.eval() + right.eval()
    }

    data class Minus(val left: Expression, val right: Expression) : Expression {
        override fun eval(): Int = left.eval() - right.eval()
    }

    data class Times(val left: Expression, val right: Expression) : Expression {
        override fun eval(): Int = left.eval() * right.eval()
    }

    data class Divide(val left: Expression, val right: Expression) : Expression {
        override fun eval(): Int = left.eval() / right.eval()
    }
}

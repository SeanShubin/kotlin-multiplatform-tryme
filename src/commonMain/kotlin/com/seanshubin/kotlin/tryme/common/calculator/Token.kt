package com.seanshubin.kotlin.tryme.common.calculator

interface Token {
    data class Number(val value: Int) : Token
    object Plus : Token {
        override fun toString(): String = "Plus"
    }

    object Minus : Token {
        override fun toString(): String = "Minus"
    }

    object Times : Token {
        override fun toString(): String = "Times"
    }

    object Divide : Token {
        override fun toString(): String = "Divide"
    }

    object OpenParen : Token {
        override fun toString(): String = "OpenParen"
    }

    object CloseParen : Token {
        override fun toString(): String = "CloseParen"
    }

    object Whitespace : Token {
        override fun toString(): String = "Whitespace"
    }
}

package com.seanshubin.kotlin.tryme.common.assembler

import com.seanshubin.kotlin.tryme.common.matcher.Tree

interface Token {
    companion object {
        data class Number(val value: Int) : Token {
            companion object {
                fun assemble(tree: Tree<Char>): Number {
                    var value = 0
                    tree.values().forEach {
                        value = value * 10 + digitToInt(it)
                    }
                    return Number(value)
                }
            }
        }

        object OpenParen : Token {
            override fun toString(): String = "OpenParen"
        }

        object CloseParen : Token {
            override fun toString(): String = "CloseParen"
        }

        object Plus : Token {
            override fun toString(): String = "Plus"
        }

        object Whitespace : Token {
            override fun toString(): String = "Whitespace"
        }

        fun digitToInt(char: Char): Int = char - '0'

        fun assemble(name: String, tree: Tree<Char>): Token {
            return when (name) {
                "number" -> Number.assemble(tree)
                "open-paren" -> OpenParen
                "close-paren" -> CloseParen
                "plus" -> Plus
                "whitespace-block" -> Whitespace
                else -> throw RuntimeException("Unable to assemble a '$name'")
            }
        }
    }

}
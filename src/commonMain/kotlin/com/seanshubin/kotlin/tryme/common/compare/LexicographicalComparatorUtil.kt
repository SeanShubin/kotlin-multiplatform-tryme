package com.seanshubin.kotlin.tryme.common.compare

object LexicographicalComparatorUtil {
    interface StateMachine {
        fun list(): List<String>
        fun append(c: Char): StateMachine
    }

    class InitialState : StateMachine {
        override fun list(): List<String> = listOf()
        override fun append(c: Char): StateMachine =
            if (isDigit(c)) DigitState(listOf(), c.toString())
            else NonDigitState(listOf(), c.toString())
    }

    class DigitState(private val words: List<String>, private val word: String) : StateMachine {
        override fun list(): List<String> = words + listOf(word)

        override fun append(c: Char): StateMachine =
            if (isDigit(c)) DigitState(words, word + c)
            else NonDigitState(words + word, c.toString())
    }

    class NonDigitState(private val words: List<String>, private val word: String) : StateMachine {
        override fun list(): List<String> = words + word

        override fun append(c: Char): StateMachine =
            if (isDigit(c)) DigitState(words + word, c.toString())
            else NonDigitState(words, word + c)
    }

    fun splitNumbers(s: String): List<String> {
        var accumulator: StateMachine = InitialState()
        for (c in s) {
            accumulator = accumulator.append(c)
        }
        return accumulator.list()
    }

    fun isDigit(c: Char): Boolean = "0123456789".contains(c)
}

package com.seanshubin.kotlin.tryme.common.parser

interface Rule {
    val name: String
    fun apply(originalResult: Result): Result
}

class RuleSeq(override val name: String, private vararg val rules: Rule) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        var result = originalResult
        for (rule in rules) {
            result = rule.apply(result)
            if (result.isError) break
        }
        return result
    }
}

class RuleName(override val name: String) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        var result = originalResult
        val rule = originalResult.ruleMap[name]!!
        result = rule.apply(result)
        return result
    }
}

class ZeroOrMore(override val name: String, private val rule: Rule) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        var resultToReturn = originalResult
        var result = resultToReturn
        while (result.isSuccess) {
            resultToReturn = result
            result = rule.apply(resultToReturn)
        }
        return resultToReturn
    }
}

class ZeroOrOne(override val name: String, private val rule: Rule) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        val result = rule.apply(originalResult)
        return if (result.isSuccess) {
            result
        } else {
            originalResult
        }
    }
}

class OneOrMore(override val name: String, private val rule: Rule) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        val result = rule.apply(originalResult)
        return if (result.isError) {
            result
        } else {
            ZeroOrMore(name, rule).apply(result)
        }
    }
}

class OneOf(override val name: String, private vararg val rules: Rule) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        var result = originalResult
        for (rule in rules) {
            result = rule.apply(originalResult)
            if (result.isSuccess) break
        }
        if (result.isError) {
            val ruleNameList = rules.map(Rule::name)
            val ruleNamesAsString = ruleNameList.joinToString(", ")
            val errorMessage = "Expected one of $ruleNamesAsString"
            result = result.copy(errorMessage = errorMessage)
        }
        return result
    }
}

class Literal(override val name: String, private val c: Char) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        return if (originalResult.cursor.valueIs(c)) {
            originalResult.copy(cursor = originalResult.cursor.next(), trees = listOf(Leaf(c)))
        } else {
            originalResult.copy(errorMessage = "Character '$c' expected")
        }
    }
}

class CharInString(override val name: String, private val s: String) : Rule {
    override fun apply(originalResult: Result): Result {
        println("$name $this")
        fun createCharRule(c: Char): Rule = Literal(name, c)
        val charRules = s.map(::createCharRule)
        return OneOf(name, *charRules.toTypedArray()).apply(originalResult)
    }
}

package com.seanshubin.kotlin.tryme.common.parser

fun main(args: Array<String>) {
    val plus = Literal("plus", '+')
    val minus = Literal("minus", '-')
    val plusTerm = RuleSeq("plusTerm", plus, RuleName("term"))
    val minusTerm = RuleSeq("minusTerm", minus, RuleName("term"))
    val plusOrMinusTerm = OneOf("plusOrMinusTerm", plusTerm, minusTerm)
    val termTail = ZeroOrMore("termTail", plusOrMinusTerm)
    val expr: Rule = RuleSeq("expr", RuleName("term"), termTail)
    val times = Literal("times", '*')
    val divide = Literal("divide", '/')
    val timesFactor = RuleSeq("timesFactor", times, RuleName("factor"))
    val divideFactor = RuleSeq("divideFactor", divide, RuleName("factor"))
    val timesOrDivideFactor = OneOf("timesOrDivideFactor", timesFactor, divideFactor)
    val factorTail = ZeroOrMore("factorTail", timesOrDivideFactor)
    val term: Rule = RuleSeq("term", RuleName("factor"), factorTail)
    val maybeMinus = ZeroOrOne("maybeMinus", minus)
    val openParen = Literal("openParen", '(')
    val closeParen = Literal("closeParen", ')')
    val exprInParens = RuleSeq("exprInParens", openParen, RuleName("expr"), closeParen)
    val exprOrNumber = OneOf("numberOrExpr", exprInParens, RuleName("number"))
    val factor: Rule = RuleSeq("factor", maybeMinus, exprOrNumber)
    val digit = CharInString("digit", "0123456789")
    val number: Rule = OneOrMore("number", digit)
    val parser = Parser(expr, term, factor, number)
    val result = parser.parse("expr", "1+2+3")
    println(result.cursor)
    println(result.errorMessage)
    println(result.trees)
}

/*
Expr ::= Term ('+' Term | '-' Term)*
Term ::= Factor ('*' Factor | '/' Factor)*
Factor ::= ['-'] (Number | '(' Expr ')')
Number ::= Digit+
*/

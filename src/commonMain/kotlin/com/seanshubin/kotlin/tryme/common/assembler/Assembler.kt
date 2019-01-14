package com.seanshubin.kotlin.tryme.common.assembler

import com.seanshubin.kotlin.tryme.common.matcher.Tree

interface Assembler {
    fun assemble(name: String, tree: Tree<Char>): Token
}

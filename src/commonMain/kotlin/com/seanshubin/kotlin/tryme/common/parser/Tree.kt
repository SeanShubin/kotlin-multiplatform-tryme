package com.seanshubin.kotlin.tryme.common.parser

interface Tree
data class Leaf(val value: Char) : Tree
data class Branch(val left: Tree, val right: Tree)
package com.seanshubin.kotlin.tryme.common.cursor

interface DetailCursor<ElementType, out DetailType> : Cursor<ElementType> {
    val detail: DetailType
    override fun next(): DetailCursor<ElementType, DetailType>
}
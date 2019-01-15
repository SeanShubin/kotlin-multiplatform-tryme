package com.seanshubin.kotlin.tryme.common.cursor

interface RowColCursor<ElementType> : DetailCursor<ElementType, RowCol> {
    override fun next(): RowColCursor<ElementType>
}

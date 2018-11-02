package com.seanshubin.kotlin.tryme.common.format

object MillisecondsFormat {
    private data class Scale(val quantity: Long, val name: String)
    private data class Segment(val scaleName: String, val quantity: Long) {
        override fun toString(): String {
            return if (quantity == 1L) "$quantity $scaleName"
            else "$quantity $scaleName" + "s"
        }
    }

    private data class Builder(val remainValue: Long, val segments: List<Segment>)

    private val millisecondScale = Scale(1000, "millisecond")
    private val secondScale = Scale(60, "second")
    private val minuteScale = Scale(60, "minute")
    private val hourScale = Scale(24, "hour")
    private val dayScale = Scale(Long.MAX_VALUE, "day")
    private val scales = listOf(millisecondScale, secondScale, minuteScale, hourScale, dayScale)
    private fun appendSegment(builder: Builder, scale: Scale): Builder {
        val current = builder.remainValue % scale.quantity
        val remain = builder.remainValue / scale.quantity
        val segment = Segment(scale.name, current)
        return Builder(remain, builder.segments + segment)
    }

    fun format(milliseconds: Long): String {
        val initial = Builder(milliseconds, emptyList())
        val builder = scales.fold(initial, ::appendSegment)
        val segments = builder.segments.filter { it.quantity != 0L }
        return if (segments.isEmpty()) builder.segments.first().toString()
        else segments.map(Segment::toString).reversed().joinToString(" ")
    }
}

package com.seanshubin.tryme.common

data class Metric(val quantity:Long, val name:String){
    companion object {
        val Milliseconds = listOf(
                Metric(1000, "millisecond"),
                Metric(60, "second"),
                Metric(60, "minute"),
                Metric(24, "hour"),
                Metric(Long.MAX_VALUE, "day"))
        fun format(x:Long, metrics:List<Metric>):String {
            val stringParts = mutableListOf<String>()
            var remain = x
            for (metric in metrics) {
                val (part, newRemain) = computeStringPart(remain, metric)
                remain = newRemain
                stringParts.add(part)
            }
            return stringParts.reversed().joinToString(" ")
        }
        fun computeStringPart(x:Long, metric:Metric):PartAndRemain {
            val (quantity, name) = metric
            val (quotient, remainder) = x divMod quantity
            val pluralizedName = pluralize(name, remainder)
            val part = "$remainder $pluralizedName"
            return PartAndRemain(part, quotient)
        }
        fun pluralize(s:String, quantity:Long):String = if(quantity == 1L) s else "${s}s"
        fun parse(s:String, metrics:List<Metric>):Long {
            val parts = s.split(" ").chunked(2).map(::fromList)

            TODO()
        }
        fun formatMilliseconds(x:Long):String = format(x, Milliseconds)
        fun parseMilliseconds(s:String):Long = parse(s, Milliseconds)
        infix fun Long.divMod(denominator:Long):QuotientAndRemainder =
                QuotientAndRemainder(this / denominator, this % denominator)
        fun fromList(list:List<String>):Metric {
            if(list.size == 2){
                val quantity = list[0].toLong()
                val name = list[1]
                return Metric(quantity, name)
            } else {
                throw RuntimeException("Expected list of size 2, got ${list.size}: $list")
            }
        }
    }

    data class PartAndRemain(val part:String, val remain:Long)
    data class QuotientAndRemainder(val quotient:Long, val remainder:Long)

}

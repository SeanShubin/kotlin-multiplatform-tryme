package com.seanshubin.tryme.jvm

import com.seanshubin.tryme.common.CommonGreeter
import com.seanshubin.tryme.common.GreeterContract

class JvmGreeter(private val commonGreeter: CommonGreeter):GreeterContract {
    override fun greet(target: String): String = commonGreeter.greet(target)
}

package com.seanshubin.tryme.js

import com.seanshubin.tryme.common.CommonGreeter
import com.seanshubin.tryme.common.GreeterContract

class JsGreeter(private val commonGreeter: CommonGreeter):GreeterContract {
    override fun greet(target: String): String = commonGreeter.greet(target)
}

package com.seanshubin.tryme.common

class CommonGreeter:GreeterContract{
    override fun greet(target:String) = "Hello, $target!"
}

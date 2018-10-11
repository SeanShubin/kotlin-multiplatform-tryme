package com.seanshubin.tryme.js

import kotlin.browser.document

fun main(args: Array<String>) {
    val h1 = document.createElement("h1")
    h1.textContent = "Hello, world!"
    document.body!!.append(h1)
}

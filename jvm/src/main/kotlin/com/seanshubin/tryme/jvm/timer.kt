package com.seanshubin.tryme.jvm

fun timer(f: () -> Unit): Long {
    val begin = System.currentTimeMillis()
    f()
    val end = System.currentTimeMillis()
    return end - begin
}

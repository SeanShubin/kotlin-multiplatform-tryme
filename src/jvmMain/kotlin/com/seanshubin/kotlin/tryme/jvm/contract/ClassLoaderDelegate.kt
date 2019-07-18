package com.seanshubin.kotlin.tryme.jvm.contract

class ClassLoaderDelegate(val delegate: ClassLoader) {
    init {
        this.javaClass.classLoader
    }
}
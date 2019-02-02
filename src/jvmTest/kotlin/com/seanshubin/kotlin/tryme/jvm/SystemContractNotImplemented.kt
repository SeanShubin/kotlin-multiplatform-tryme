package com.seanshubin.kotlin.tryme.jvm

import com.seanshubin.kotlin.tryme.jvm.contract.SystemContract
import java.io.Console
import java.io.InputStream
import java.io.PrintStream
import java.nio.channels.Channel
import java.util.*

abstract class SystemContractNotImplemented : SystemContract {
    override fun setIn(`in`: InputStream) {
        throw NotImplementedError("not implemented")
    }

    override fun setOut(out: PrintStream) {
        throw NotImplementedError("not implemented")
    }

    override fun setErr(err: PrintStream) {
        throw NotImplementedError("not implemented")
    }

    override fun console(): Console {
        throw NotImplementedError("not implemented")
    }

    override fun inheritedChannel(): Channel {
        throw NotImplementedError("not implemented")
    }

    override fun currentTimeMillis(): Long {
        throw NotImplementedError("not implemented")
    }

    override fun nanoTime(): Long {
        throw NotImplementedError("not implemented")
    }

    override fun arraycopy(src: Any, srcPos: Int, dest: Any, destPos: Int, length: Int) {
        throw NotImplementedError("not implemented")
    }

    override fun identityHashCode(x: Any): Int {
        throw NotImplementedError("not implemented")
    }

    override fun lineSeparator(): String? {
        throw NotImplementedError("not implemented")
    }

    override fun getProperty(key: String): String? {
        throw NotImplementedError("not implemented")
    }

    override fun getProperty(key: String, def: String): String {
        throw NotImplementedError("not implemented")
    }

    override fun setProperty(key: String, value: String): String {
        throw NotImplementedError("not implemented")
    }

    override fun clearProperty(key: String): String {
        throw NotImplementedError("not implemented")
    }

    override fun getenv(name: String): String {
        throw NotImplementedError("not implemented")
    }

    override fun getenv(): Map<String, String> {
        throw NotImplementedError("not implemented")
    }

    override fun getLogger(name: String): System.Logger {
        throw NotImplementedError("not implemented")
    }

    override fun getLogger(name: String, bundle: ResourceBundle): System.Logger {
        throw NotImplementedError("not implemented")
    }

    override fun exit(status: Int) {
        throw NotImplementedError("not implemented")
    }

    override fun gc() {
        throw NotImplementedError("not implemented")
    }

    override fun runFinalization() {
        throw NotImplementedError("not implemented")
    }

    override fun load(filename: String) {
        throw NotImplementedError("not implemented")
    }

    override fun loadLibrary(libname: String) {
        throw NotImplementedError("not implemented")
    }

    override fun mapLibraryName(libname: String): String {
        throw NotImplementedError("not implemented")
    }
}
package com.seanshubin.kotlin.tryme.common.functional

interface Try<T> {
    fun <U> map(f: (T) -> U): Try<U>
    fun <U> flatMap(f: (T) -> Try<U>): Try<U>

    companion object {
        operator fun <T> invoke(f: () -> T): Try<T> =
            try {
                Success(f())
            } catch (throwable: Throwable) {
                Failure(throwable)
            }
    }

    data class Success<T>(val value: T) : Try<T> {
        override fun <U> map(f: (T) -> U): Try<U> = Try.invoke { f(value) }

        override fun <U> flatMap(f: (T) -> Try<U>): Try<U> = try {
            f(value)
        } catch (throwable: Throwable) {
            Failure(throwable)
        }
    }

    data class Failure<T>(val exception: Throwable) : Try<T> {
        override fun <U> map(f: (T) -> U): Try<U> = Failure(exception)

        override fun <U> flatMap(f: (T) -> Try<U>): Try<U> = Failure(exception)
    }
}

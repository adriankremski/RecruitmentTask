package com.github.snuffix.recruitmenttask.domain.model


/**
 * Wrapper class for passing data between 'data', 'presentation' and 'domain' modules
 */
sealed class Result<out T : Any> {

    class Ok<out T : Any>(val value: T) : Result<T>()
    class CancelledError : Error()
    open class Error(val exception: Exception? = null) : Result<Nothing>()

    inline fun whenOk(block: Ok<T>.() -> Unit): Result<T> {
        if (this is Ok) {
            this.block()
        }
        return this
    }

    inline fun whenError(block: Error.() -> Unit): Result<T> {
        // CancelledError can only occur when coroutine job was cancelled and we don't need to handle it
        if (this is Error && this !is CancelledError) {
            this.block()
        }
        return this
    }
}


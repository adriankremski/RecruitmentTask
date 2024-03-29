package com.github.snuffix.recruitmenttask.presentation.model

/**
 * Model that will be used for passing data to UI module
 */
open class Resource<out T : Any> {

    class Success<T : Any>(val data: T) : Resource<T>()

    class Loading<T : Any> : Resource<T>()

    class Error<T : Any>(val message: String? = null, val errorType: ErrorType) : Resource<T>()

    companion object {
        fun <T : Any> error(message: String? = null) = Error<T>(message, ErrorType.ERROR)
        fun <T : Any> networkError(message: String? = null) = Error<T>(message, ErrorType.NETWORK)
    }
}

enum class ErrorType {
    NETWORK, ERROR
}
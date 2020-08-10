package com.currenciesapp.common

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: CicError) : Result<Nothing>()

    operator fun invoke(): T? = (this as? Success)?.data
}

fun <T> success(data: T) = Result.Success(data)

inline infix fun <T, V2> Result<T>.map(f: (T) -> V2): Result<V2> = when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(f(data))
}

inline infix fun <T> Result<T>.doOnSuccess(call: (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        call(data)
    }
    return this
}

inline fun <T> safeCall(call: () -> T): Result<T> =
    try {
        Result.Success(call.invoke())
    } catch (exception: Exception) {
        Result.Failure(exception.toCicError())
    }

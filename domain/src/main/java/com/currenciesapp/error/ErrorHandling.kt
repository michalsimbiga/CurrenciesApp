package com.currenciesapp.error

import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val TOKEN_INVALID = "The provided token is already invalid"

fun Throwable.toMyError() =
    when (this) {
        is MyApiException -> toMyError()
        is SocketTimeoutException -> toMyError()
        is UnknownHostException -> toMyError()
        else -> throw this
    }

internal fun MyApiException.toMyError(): MyError = when {
    code >= HttpURLConnection.HTTP_INTERNAL_ERROR -> MyError.ServerError(this)
    code == HttpURLConnection.HTTP_BAD_REQUEST -> MyError.BadRequest(this)
    code == HttpURLConnection.HTTP_NOT_FOUND -> validateTokenMessage(this)
    else -> MyError.ServerError(this)
}

private fun validateTokenMessage(apiException: MyApiException): MyError {
    return if (apiException.message?.contains(TOKEN_INVALID, true) == true) {
        MyError.TokenInvalid(apiException)
    } else {
        MyError.ServerError(apiException)
    }
}

internal fun SocketTimeoutException.toMyError(): MyError =
    MyError.ConnectionNotEstablished(this)

internal fun UnknownHostException.toMyError(): MyError =
    MyError.ConnectionNotEstablished(this)

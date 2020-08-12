package com.currenciesapp.error

import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
    else -> MyError.ServerError(this)
}

internal fun SocketTimeoutException.toMyError(): MyError =
    MyError.ConnectionNotEstablished(this)

internal fun UnknownHostException.toMyError(): MyError =
    MyError.ConnectionNotEstablished(this)

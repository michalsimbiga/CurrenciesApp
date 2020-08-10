package com.fieldcode.customerinformationcenter.domain.error

import com.currenciesapp.error.MyApiException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val TOKEN_INVALID = "The provided token is already invalid"

fun Throwable.toCicError() =
    when (this) {
        is MyApiException -> toCicError()
        is SocketTimeoutException -> toCicError()
        is UnknownHostException -> toCicError()
        else -> throw this
    }

internal fun MyApiException.toCicError(): CicError = when {
    code >= HttpURLConnection.HTTP_INTERNAL_ERROR -> CicError.ServerError(this)
    code == HttpURLConnection.HTTP_BAD_REQUEST -> CicError.BadRequest(this)
    code == HttpURLConnection.HTTP_NOT_FOUND -> validateTokenMessage(this)
    else -> CicError.ServerError(this)
}

private fun validateTokenMessage(apiException: MyApiException): CicError {
    return if (apiException.message?.contains(TOKEN_INVALID, true) == true) {
        CicError.TokenInvalid(apiException)
    } else {
        CicError.ServerError(apiException)
    }
}

internal fun SocketTimeoutException.toCicError(): CicError =
    CicError.ConnectionNotEstablished(this)

internal fun UnknownHostException.toCicError(): CicError =
    CicError.ConnectionNotEstablished(this)

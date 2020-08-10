package com.fieldcode.customerinformationcenter.domain.error

sealed class CicError : CicException() {

    data class ServerError(override val originalException: Throwable? = null) : CicError()

    data class BadRequest(override val originalException: Throwable? = null) : CicError()

    data class TokenInvalid(override val originalException: Throwable? = null) : CicError()

    data class ConnectionNotEstablished(override val originalException: Throwable? = null) :
        CicError()

    data class Unsynchronized(override val originalException: Throwable? = null) : CicError()
}

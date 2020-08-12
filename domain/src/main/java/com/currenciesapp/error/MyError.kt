package com.currenciesapp.error

sealed class MyError : MyException() {

    data class ServerError(override val originalException: Throwable? = null) : MyError()

    data class BadRequest(override val originalException: Throwable? = null) : MyError()

    data class ConnectionNotEstablished(override val originalException: Throwable? = null) :
        MyError()
}

package com.currenciesapp.error

import retrofit2.Response

data class MyApiException(
    val code: Int,
    val url: String,
    override val message: String?,
    override val originalException: Throwable? = null
) : MyException() {

    constructor(response: Response<*>) : this(
        response.code(),
        response.raw().request().url().toString(),
        response.errorBody()?.string()
    )

    override fun toString() = "CODE: $code \nURL: $url \nMESSAGE: $message"
}

package com.currenciesapp.error

import com.fieldcode.customerinformationcenter.domain.error.CicException

data class MyApiException(
    val code: Int,
    val url: String,
    override val message: String?,
    override val originalException: Throwable? = null
) : CicException() {

    constructor(response: Response<*>) : this(
        response.code(),
        response.raw().request().url().toString(),
        response.errorBody()?.string()
    )

    override fun toString() = "CODE: $code \nURL: $url \nMESSAGE: $message"
}

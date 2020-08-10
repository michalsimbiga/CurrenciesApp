package com.currenciesapp.common.extensions

import com.currenciesapp.error.MyApiException
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
fun <A : Any?> Response<A>.bodyOrException(): A {
    val body = body()
    return if (isSuccessful) {
        body ?: Unit as A
    } else {
        throw MyApiException(this)
    }
}
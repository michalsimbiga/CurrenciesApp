package com.currenciesapp

import com.currenciesapp.model.RatesResponse
import okhttp3.ResponseBody
import retrofit2.Converter

class RatesResponseConverter<T>(
    private val delegate: Converter<ResponseBody, RatesResponse<T>>
) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? = delegate.convert(value)?.data
}
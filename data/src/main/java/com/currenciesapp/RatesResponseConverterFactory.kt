package com.currenciesapp

import com.currenciesapp.model.RatesResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

//object RatesResponseConverterFactory : Converter.Factory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun responseBodyConverter(
//        type: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): Converter<ResponseBody, *>? {
//
//        val wrappedType = object : ParameterizedType {
//            override fun getRawType() = RatesResponse::class.java
//            override fun getOwnerType() = null
//            override fun getActualTypeArguments() = arrayOf(type)
//        }
//
//        val delegate =
//            retrofit.nextResponseBodyConverter<Converter<ResponseBody, RatesResponse<*>>>(
//                this, wrappedType, annotations
//            ) as Converter<ResponseBody, RatesResponse<Any>>
//
//        return RatesResponseConverter(delegate)
//    }
//}
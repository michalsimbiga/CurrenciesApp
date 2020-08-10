package com.currenciesapp.common.usecase

import com.currenciesapp.common.Result
import com.currenciesapp.error.MyError

abstract class NoParameterUseCase<out Type> where Type : Any {

    abstract suspend fun run(): Result<Type>

    suspend operator fun invoke(
        onSuccess: (Type) -> Unit = {},
        onFailure: (MyError) -> Unit = {}
    ) = when (val result = run()) {
        is Result.Success -> onSuccess(result.data)
        is Result.Failure -> onFailure(result.error)
    }
}
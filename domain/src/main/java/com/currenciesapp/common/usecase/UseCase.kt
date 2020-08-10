package com.currenciesapp.common.usecase

import com.currenciesapp.common.Result
import com.currenciesapp.error.MyError

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    suspend operator fun invoke(
        params: Params,
        onSuccess: (Type) -> Unit = {},
        onFailure: (MyError) -> Unit = {}
    ) = when (val result = run(params)) {
        is Result.Success -> onSuccess(result.data)
        is Result.Failure -> onFailure(result.error)
    }
}

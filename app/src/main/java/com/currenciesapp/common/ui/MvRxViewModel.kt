package com.currenciesapp.common.ui

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Success
import com.currenciesapp.common.useCase.UseCase

abstract class MvRxViewModel<State : MvRxState>(
    private val initialState: State
) : BaseMvRxViewModel<State>(initialState, debugMode = BuildConfig.DEBUG) {

    suspend fun <Type : Any, Type2, Param> UseCase<Type, Param>.execute(
        params: Param,
        mapper: ((Type) -> Type2),
        stateReducer: State.(Async<Type2>) -> State
    ) {
        invoke(
            params = params,
            onSuccess = { setState { stateReducer(Success(mapper(it))) } },
            onFailure = { setState { stateReducer(Fail(it)) } }
        )
    }
}

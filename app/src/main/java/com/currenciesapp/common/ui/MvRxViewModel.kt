package com.currenciesapp.common.ui

import com.airbnb.mvrx.*
import com.currenciesapp.common.useCase.NoParameterUseCase
import com.currenciesapp.common.useCase.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class MvRxViewModel<State : MvRxState>(
    private val initialState: State
) : BaseMvRxViewModel<State>(initialState, debugMode = BuildConfig.DEBUG) {

    private val viewModelJob = SupervisorJob()

    protected val backgroundScope = CoroutineScope(viewModelJob + Dispatchers.Default)

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    suspend fun <Type : Any, Param> UseCase<Type, Param>.execute(
        params: Param,
        stateReducer: State.(Async<Type>) -> State
    ) {
        setState { stateReducer(Loading()) }
        invoke(
            params,
            onSuccess = { setState { stateReducer(Success(it)) } },
            onFailure = { setState { stateReducer(Fail(it)) } }
        )
    }

    suspend fun <Type : Any, Type2, Param> UseCase<Type, Param>.execute(
        params: Param,
        mapper: ((Type) -> Type2),
        stateReducer: State.(Async<Type2>) -> State
    ) {
        setState { stateReducer(Loading()) }
        invoke(
            params,
            onSuccess = { setState { stateReducer(Success(mapper(it))) } },
            onFailure = { setState { stateReducer(Fail(it)) } }
        )
    }

    suspend fun <Type : Any, Type2> NoParameterUseCase<Type>.execute(
        mapper: ((Type) -> Type2),
        stateReducer: State.(Async<Type2>) -> State
    ) {
        setState { stateReducer(Loading()) }
        invoke(
            onSuccess = { setState { stateReducer(Success(mapper(it))) } },
            onFailure = { setState { stateReducer(Fail(it)) } }
        )
    }

    suspend fun <Type : Any> NoParameterUseCase<Type>.execute(
        stateReducer: State.(Async<Type>) -> State
    ) {
        setState { stateReducer(Loading()) }
        invoke(
            onSuccess = { setState { stateReducer(Success(it)) } },
            onFailure = { setState { stateReducer(Fail(it)) } }
        )
    }
}

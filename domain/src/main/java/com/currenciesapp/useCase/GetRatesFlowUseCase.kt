package com.currenciesapp.useCase

import com.currenciesapp.common.Result
import com.currenciesapp.common.useCase.UseCase
import com.currenciesapp.model.Rates
import com.currenciesapp.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetRatesFlowUseCase(private val currencyRepository: CurrencyRepository) :
    UseCase<Flow<Rates>, GetRatesFlowUseCase.Params>() {

    override suspend fun run(params: Params): Result<Flow<Rates>> =
        currencyRepository.getRatesFlowFor(params.currencyName)

    data class Params(val currencyName: String)
}
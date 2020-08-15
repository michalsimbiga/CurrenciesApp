package com.currenciesapp.useCase

import com.currenciesapp.common.Result
import com.currenciesapp.common.useCase.UseCase
import com.currenciesapp.repository.CurrencyRepository

class GetRatesUseCase(private val currencyRepository: CurrencyRepository) :
    UseCase<Unit, GetRatesUseCase.Params>() {

    override suspend fun run(params: Params): Result<Unit> =
        currencyRepository.fetchRatesFor(params.currencyName)

    data class Params(val currencyName: String)
}
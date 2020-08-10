package com.currenciesapp.useCase

import com.currenciesapp.common.Result
import com.currenciesapp.common.useCase.UseCase
import com.currenciesapp.model.Currency
import com.currenciesapp.repository.CurrencyRepository

class GetRatesUseCase(private val currencyRepository: CurrencyRepository) :
    UseCase<List<Currency>, GetRatesUseCase.Params>() {

    override suspend fun run(params: Params): Result<List<Currency>> =
        currencyRepository.getRates(params.currencyName)

    data class Params(val currencyName: String)
}
package com.currenciesapp

import com.currenciesapp.common.Result
import com.currenciesapp.repository.CurrencyRepository
import com.currenciesapp.useCase.GetRatesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetRatesUseCaseTest {

    private lateinit var useCase: GetRatesUseCase
    private val repository: CurrencyRepository = mockk(relaxed = true)
    private val currencyNameParams = GetRatesUseCase.Params("EUR")
    private val mockedCurrencyList: List<com.currenciesapp.model.Currency> = mockk(relaxed = true)

    @Before
    fun setup() {
        useCase = GetRatesUseCase(currencyRepository = repository)
    }

    @Test
    fun `When save ticket data invoked then call repository to save ticket data`() =
        runBlockingTest {
            coEvery { repository.getRates(any()) } returns Result.Success(mockedCurrencyList)

            useCase.invoke(currencyNameParams)

            coVerify { repository.getRates(currencyNameParams.currencyName) }
        }
}

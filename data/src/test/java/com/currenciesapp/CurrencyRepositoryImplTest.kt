package com.currenciesapp

import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.model.RatesResponse
import com.currenciesapp.repository.CurrencyRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CurrencyRepositoryImplTest {

    private lateinit var currencyRepositoryImpl: CurrencyRepositoryImpl

    private val remoteDataSource: RemoteDataSource = mockk(relaxed = true)
    private val mockedRatesResponse: RatesResponse = mockk(relaxed = true)

    @Before
    fun setup() {
        currencyRepositoryImpl = CurrencyRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Test
    fun `When getRates invoked call remote data source to fetch rates data`() =
        runBlocking {
            val exampleCurrency = "EUR"
            coEvery { remoteDataSource.getRates(any()) } returns mockedRatesResponse

            currencyRepositoryImpl.getRates(exampleCurrency)

            coVerify { remoteDataSource.getRates(exampleCurrency) }
        }
}
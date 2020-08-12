package com.currenciesapp

import com.currenciesapp.api.CurrencyApi
import com.currenciesapp.dataSource.RemoteDataSource
import com.currenciesapp.model.RatesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource

    private val currencyApi: CurrencyApi = mockk(relaxed = true)
    private val mockedRatesResponse: RatesResponse = mockk(relaxed = true)

    @Before
    fun setup() {
        remoteDataSource = RemoteDataSource(currencyApi)
    }

    @Test
    fun `When remoteDataSource getRates invoked then currencyApi method is called`() = runBlocking {
        val exampleCurrency = "EUR"
        coEvery { currencyApi.getRatesFor(any()) } returns Response.success(mockedRatesResponse)

        remoteDataSource.getRates(exampleCurrency)

        coVerify { currencyApi.getRatesFor(exampleCurrency) }
    }
}
package com.currenciesapp

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.currenciesapp.dao.RatesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.ext.scope
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class RatesDaoTest {

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val testScope: TestCoroutineScope = TestCoroutineScope(testDispatcher)

    private lateinit var ratesDao: RatesDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room
            .inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
        ratesDao = db.ratesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertRatesToDatabase() = testScope.runBlockingTest {
        val baseCurrency = mockedRatesEur.baseCurrency
        val currencyRatesFlow = ratesDao.getRatesByCurrency(baseCurrency).filterNotNull()

        currencyRatesFlow.test(this).assertNoValues().finish()

        ratesDao.insertRates(mockedRatesEur)

        currencyRatesFlow
            .test(this)
            .assertValues(mockedRatesEur)
            .finish()
    }

    @Test
    fun testInsertNewRatesUpdatesTheDatabase() = testScope.runBlockingTest {
        val baseCurrency = mockedRatesEur.baseCurrency
        val currencyRatesFlow = ratesDao.getRatesByCurrency(baseCurrency).filterNotNull()

        currencyRatesFlow.test(this).assertNoValues().finish()

        ratesDao.insertRates(mockedRatesEur)
        ratesDao.insertRates(mockedRatesEurSecond)

        currencyRatesFlow.test(this).assertValues(mockedRatesEur, mockedRatesEurSecond).finish()
    }
}
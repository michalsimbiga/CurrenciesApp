package com.currenciesapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.currenciesapp.dao.RatesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
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
        val observer = ratesDao.getRatesByCurrency(baseCurrency).filterNotNull().test(this)

        observer.assertNoValues()

        ratesDao.insertRates(mockedRatesEur)

        observer.assertValues(mockedRatesEur)
        observer.assertNrOfItems(1)

        observer.finish()
    }

    @Test
    fun testInsertNewRatesUpdatesTheDatabase() = testScope.runBlockingTest {
        val baseCurrency = mockedRatesEur.baseCurrency
        val observer = ratesDao.getRatesByCurrency(baseCurrency).filterNotNull().test(this)

        observer.assertNoValues()

        ratesDao.insertRates(mockedRatesEur)

        observer.assertNrOfItems(1)
        observer.assertValues(mockedRatesEur)

        ratesDao.insertRates(mockedRatesEurSecond)

        observer.assertNrOfItems(2)
        observer.assertValues(mockedRatesEur, mockedRatesEurSecond)

        observer.finish()
    }
}
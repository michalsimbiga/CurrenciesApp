package com.currenciesapp

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.currenciesapp.dao.RatesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class RatesDaoTest {

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope

    private lateinit var ratesDao: RatesDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(testDispatcher)
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
        ratesDao.insertRates(mockedRatesEur)

        val listOfResults = withContext(testDispatcher) {
            ratesDao.getRatesByCurrency(mockedRatesEur.baseCurrency).take(1).toList()
        }

        assertThat(listOfResults.first(), equalTo(mockedRatesEur))
    }

    @Test
    fun testInsertNewRatesUpdatesTheDatabase() = testScope.runBlockingTest {
        ratesDao.insertRates(mockedRatesEur)
        ratesDao.insertRates(mockedRatesEurSecond)

        val listOfResults = withContext(testDispatcher) {
            ratesDao.getRatesByCurrency(mockedRatesEur.baseCurrency).take(2).toList()
        }

        assertThat(listOfResults, equalTo(listOf(mockedRatesEur, mockedRatesEurSecond)))
    }
}
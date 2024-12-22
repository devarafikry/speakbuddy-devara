package jp.speakbuddy.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSourceImpl
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDao
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDatabase
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseFactDataSourceTest {
    private lateinit var database: FactDatabase
    private lateinit var factDao: FactDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, FactDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        factDao = database.factDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsertDatabase() {
        val databaseDataSource = DatabaseFactDataSourceImpl(
            factDao = factDao
        )
        runTest {
            val insertedRow = databaseDataSource.insertFactData(
                factEntity = FactEntity(
                    fact = "test",
                    length = 4
                )
            )

            assertEquals(1L, insertedRow)
        }
    }

    @Test
    fun testInsertAndReadDatabase() {
        val databaseDataSource = DatabaseFactDataSourceImpl(
            factDao = factDao
        )
        val testFact = FactEntity(
            fact = "test",
            length = 4
        )

        runTest {
            databaseDataSource.insertFactData(
                factEntity = testFact
            )
            val factData = databaseDataSource.getRoomFactData()
            assertEquals(testFact.fact, factData?.fact)
            assertEquals(testFact.length, factData?.length)
        }
    }

    @Test
    fun testDeleteAllFacts() {
        val databaseDataSource = DatabaseFactDataSourceImpl(
            factDao = factDao
        )
        val testFact = FactEntity(
            fact = "test",
            length = 4
        )

        runTest {
            databaseDataSource.insertFactData(testFact)
            databaseDataSource.insertFactData(testFact)
            databaseDataSource.insertFactData(testFact)
            databaseDataSource.insertFactData(testFact)
            databaseDataSource.insertFactData(testFact)

            databaseDataSource.deleteAllFacts()
            val factData = databaseDataSource.getRoomFactData()
            assertEquals(null, factData)
        }
    }
}
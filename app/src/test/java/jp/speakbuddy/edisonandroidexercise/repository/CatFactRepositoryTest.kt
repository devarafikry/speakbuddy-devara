package jp.speakbuddy.edisonandroidexercise.repository

import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.CloudFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.repository.CatFactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataSourceType
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModel
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModelResult
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CatFactRepositoryTest {

    @Test
    fun `when getCachedFact with cache data available then returns data from database` () {
        val cloudFactDataSource = mockk<CloudFactDataSource>()
        val databaseFactDataSource = mockk<DatabaseFactDataSource>()

        val catFactRepository = CatFactRepository(
            cloudFactDataSource = cloudFactDataSource,
            cachedCloudFactDataSource = databaseFactDataSource
        )

        coEvery { databaseFactDataSource.getRoomFactData() } returns FactEntity(
            id = 0L, fact = "", length = 0
        )

        runTest {
            val fact: FactDataModelResult = catFactRepository.getCachedFact()
            val resultDataSource = (fact as FactDataModelResult.Success).factDataModel.datasource
            assert(resultDataSource is FactDataSourceType.Database)
        }
    }

    @Test
    fun `when getCloudFact with success network request then returns data from network` () {
        val cloudFactDataSource = mockk<CloudFactDataSource>()
        val databaseFactDataSource = mockk<DatabaseFactDataSource>()

        val catFactRepository = CatFactRepository(
            cloudFactDataSource = cloudFactDataSource,
            cachedCloudFactDataSource = databaseFactDataSource
        )

        coEvery { cloudFactDataSource.getNetworkFactData() } returns FactResponse(
            fact = "", length = 1
        )
        coEvery { cloudFactDataSource.getHostName() } returns "https://google.com"
        runTest {
            val fact: FactDataModelResult = catFactRepository.getCloudFact()
            val resultDataSource = (fact as FactDataModelResult.Success).factDataModel.datasource
            assert(resultDataSource is FactDataSourceType.Network)
        }
    }

    @Test
    fun `when saveFact with success database operation then returns inserted row count` () {
        val cloudFactDataSource = mockk<CloudFactDataSource>()
        val databaseFactDataSource = mockk<DatabaseFactDataSource>()
        val expectedRow = 1L
        val catFactRepository = CatFactRepository(
            cloudFactDataSource = cloudFactDataSource,
            cachedCloudFactDataSource = databaseFactDataSource
        )

        coEvery { databaseFactDataSource.insertFactData(any()) } returns expectedRow
        runTest {
            val insertedRow = catFactRepository.saveFact(
                factDataModel = FactDataModel(
                    fact = "",
                    length = 0,
                    type = FactType.CatFact,
                    datasource = FactDataSourceType.Network(apiHost = "")
                )
            )
            assertEquals(expectedRow, insertedRow)
        }
    }

    @Test
    fun `when saveFact with failed database operation then returns 0` () {
        val cloudFactDataSource = mockk<CloudFactDataSource>()
        val databaseFactDataSource = mockk<DatabaseFactDataSource>()
        val expectedRow = 0L
        val catFactRepository = CatFactRepository(
            cloudFactDataSource = cloudFactDataSource,
            cachedCloudFactDataSource = databaseFactDataSource
        )

        coEvery { databaseFactDataSource.insertFactData(any()) } throws Exception()
        runTest {
            val insertedRow = catFactRepository.saveFact(
                factDataModel = FactDataModel(
                    fact = "",
                    length = 0,
                    type = FactType.CatFact,
                    datasource = FactDataSourceType.Network(apiHost = "")
                )
            )
            assertEquals(expectedRow, insertedRow)
        }
    }
}
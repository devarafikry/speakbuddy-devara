package jp.speakbuddy.edisonandroidexercise.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.CloudFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.repository.CatFactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataSourceType
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModel
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModelResult
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactType
import jp.speakbuddy.edisonandroidexercise.usecase.impl.GetCatFactUseCaseImpl
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCatFactUseCaseTest {

    @Test
    fun `when getCachedFact with cache data available then returns data from database` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "testing"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCachedFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Database(0L),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getCachedFact() as FactModelResult.Success)
            assertEquals(expectedFact, result.factModel.fact)
        }
    }

    @Test
    fun `when getNewFact with network request success then returns data from network` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "testing"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCloudFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Network(""),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getNewFact() as FactModelResult.Success)
            assertEquals(expectedFact, result.factModel.fact)
        }
    }

    @Test
    fun `when getCachedFact with length above 100 then shouldShowLength value must be true` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "OVBGE?(WC/E=&h.dMtt&BK(~7='AF-wv0s+M1&7qtS7{@OeiiqjbPdZ92)w13K^f_/YJTFG+Y_Z>iM(i@U_D,Cf%/8>ty*/xng[%!"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCachedFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Database(0L),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getCachedFact() as FactModelResult.Success)
            assertEquals(true, result.factModel.lengthInfo.shouldShowLength)
        }
    }

    @Test
    fun `when getNewFact with length above 100 then shouldShowLength value must be true` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "OVBGE?(WC/E=&h.dMtt&BK(~7='AF-wv0s+M1&7qtS7{@OeiiqjbPdZ92)w13K^f_/YJTFG+Y_Z>iM(i@U_D,Cf%/8>ty*/xng[%!"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCloudFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Network(""),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getNewFact() as FactModelResult.Success)
            assertEquals(true, result.factModel.lengthInfo.shouldShowLength)
        }
    }

    @Test
    fun `when getCachedFact with fact contains cats then multipleCats value must be true` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "catsOVBGE?(WC/E=&h.dMtt&BK(~7='AF-wv0s+M1&7qtS7{@OeiiqjbPdZ92)w13K^f_/YJTFG+Y_Z>iM(i@U_D,Cf%/8>ty*/xng[%!"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCachedFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Database(0L),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getCachedFact() as FactModelResult.Success)
            assertEquals(true, result.factModel.multipleCats)
        }
    }

    @Test
    fun `when getNewFact with fact contains cats then multipleCats value must be true` () {
        val factRepository = mockk<FactRepository>()
        val expectedFact = "catsOVBGE?(WC/E=&h.dMtt&BK(~7='AF-wv0s+M1&7qtS7{@OeiiqjbPdZ92)w13K^f_/YJTFG+Y_Z>iM(i@U_D,Cf%/8>ty*/xng[%!"
        val getCatFactUseCase = GetCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.getCloudFact() } returns FactDataModelResult.Success(
            factDataModel = FactDataModel(
                fact = expectedFact,
                length = 0,
                datasource = FactDataSourceType.Network(""),
                type = FactType.CatFact
            )
        )

        runTest {
            val result = (getCatFactUseCase.getNewFact() as FactModelResult.Success)
            assertEquals(true, result.factModel.multipleCats)
        }
    }
}
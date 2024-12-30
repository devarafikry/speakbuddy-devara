package jp.speakbuddy.edisonandroidexercise.usecase

import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.usecase.impl.SaveCatFactUseCaseImpl
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewDataSource
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveCatFactUseCaseTest {

    @Test
    fun `when saveFact() with successful database operation then returns insertedRow value`() {
        val factRepository = mockk<FactRepository>()
        val saveCatFactUseCase = SaveCatFactUseCaseImpl(
            factRepository = factRepository
        )

        coEvery { factRepository.saveFact(any()) } returns 1L
        runTest {
            val insertedRow = saveCatFactUseCase.saveCatFact(
                factModel = FactModel(
                    fact = "",
                    lengthInfo = LengthInfo(
                        length = 0,
                        shouldShowLength = false
                    ),
                    multipleCats = false,
                    source = FactModelViewDataSource.Cloud
                )
            )
            assertEquals(1L, insertedRow)
        }
    }
}

package jp.speakbuddy.edisonandroidexercise.ui

import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.dispatcher.TestDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.usecase.GetCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.SaveCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelResult
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewData
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo
import org.junit.Test

class FactViewModelTest {

    val cachedFact = FactModel(
        fact = "abcde",
        lengthInfo = LengthInfo(
            length = 5,
            shouldShowLength = false
        ),
        multipleCats = false
    )

    val cloudFact = FactModel(
        fact = "aaaa",
        lengthInfo = LengthInfo(
            length = 4,
            shouldShowLength = false
        ),
        multipleCats = false
    )

    @Test
    fun `when viewmodel initialized with database available then fetch state initial data should be cached data`() {
        val getCatFactUseCase = mockk<GetCatFactUseCase>()
        val saveCatFactUseCase = mockk<SaveCatFactUseCase>()

        coEvery { getCatFactUseCase.getCachedFact() } returns FactModelResult.Success(cachedFact)
        coEvery { getCatFactUseCase.getNewFact() } returns FactModelResult.Success(cloudFact)
        coEvery { saveCatFactUseCase.saveCatFact(any()) } returns 0L

        val viewModel = FactViewModel(
            getCatFactUseCase = getCatFactUseCase,
            saveCatFactUseCase = saveCatFactUseCase,
            dispatcherProvider = TestDispatcherProvider()
        )

        assert((viewModel.factStateFlow.value as FactModelViewData.Data).factModel == cachedFact)
    }

    @Test
    fun `when updateFact with network request success then fetchState should be cloud data`() {
        val getCatFactUseCase = mockk<GetCatFactUseCase>()
        val saveCatFactUseCase = mockk<SaveCatFactUseCase>()

        coEvery { getCatFactUseCase.getCachedFact() } returns FactModelResult.Success(cachedFact)
        coEvery { getCatFactUseCase.getNewFact() } returns FactModelResult.Success(cloudFact)
        coEvery { saveCatFactUseCase.saveCatFact(any()) } returns 0L

        val viewModel = FactViewModel(
            getCatFactUseCase = getCatFactUseCase,
            saveCatFactUseCase = saveCatFactUseCase,
            dispatcherProvider = TestDispatcherProvider()
        )

        viewModel.updateFact()
        assert((viewModel.factStateFlow.value as FactModelViewData.Data).factModel == cloudFact)
    }

    @Test
    fun `when updateFact with network request success and database operation success then syncDataStatus should be true`() {
        val getCatFactUseCase = mockk<GetCatFactUseCase>()
        val saveCatFactUseCase = mockk<SaveCatFactUseCase>()

        coEvery { getCatFactUseCase.getCachedFact() } returns FactModelResult.Success(cachedFact)
        coEvery { getCatFactUseCase.getNewFact() } returns FactModelResult.Success(cloudFact)

        coEvery { saveCatFactUseCase.saveCatFact(any()) } returns 1L

        val viewModel = FactViewModel(
            getCatFactUseCase = getCatFactUseCase,
            saveCatFactUseCase = saveCatFactUseCase,
            dispatcherProvider = TestDispatcherProvider()
        )

        viewModel.updateFact()
        assert(viewModel.syncDataStatus.value)
    }

    @Test
    fun `when updateFact with network request success and database operation failed then syncDataStatus should be false`() {
        val getCatFactUseCase = mockk<GetCatFactUseCase>()
        val saveCatFactUseCase = mockk<SaveCatFactUseCase>()

        coEvery { getCatFactUseCase.getCachedFact() } returns FactModelResult.Success(cachedFact)
        coEvery { getCatFactUseCase.getNewFact() } returns FactModelResult.Success(cloudFact)

        coEvery { saveCatFactUseCase.saveCatFact(any()) } throws Exception()

        val viewModel = FactViewModel(
            getCatFactUseCase = getCatFactUseCase,
            saveCatFactUseCase = saveCatFactUseCase,
            dispatcherProvider = TestDispatcherProvider()
        )

        viewModel.updateFact()
        assert(!viewModel.syncDataStatus.value)
    }
}

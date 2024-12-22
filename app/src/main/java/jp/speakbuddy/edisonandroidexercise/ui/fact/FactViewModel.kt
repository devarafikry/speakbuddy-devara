package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.dispatcher.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.usecase.GetCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.SaveCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelResult
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getCatFactUseCase: GetCatFactUseCase,
    private val saveCatFactUseCase: SaveCatFactUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _factStateFlow: MutableStateFlow<FactModelViewData> = MutableStateFlow(FactModelViewData.Empty)
    val factStateFlow: StateFlow<FactModelViewData> = _factStateFlow

    private val _syncDataStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val syncDataStatus: StateFlow<Boolean> = _syncDataStatus

    init {
        viewModelScope.launch(dispatcherProvider.default) {
            val factResult = getCatFactUseCase.getCachedFact()
            when (factResult) {
                is FactModelResult.Success -> {
                    _factStateFlow.value = FactModelViewData.Data(
                        factModel = factResult.factModel
                    )
                }

                is FactModelResult.Error -> {
                    //                    _factStateFlow.value = factResult.factModel
                }
            }
        }
    }

    fun updateFact() {
        viewModelScope.launch(dispatcherProvider.default) {
            _syncDataStatus.value = false
            val factResult = getCatFactUseCase.getNewFact()
            when (factResult) {
                is FactModelResult.Success -> {
                    _factStateFlow.value = FactModelViewData.Data(
                        factModel = factResult.factModel
                    )
                    viewModelScope.launch(dispatcherProvider.io) {
                        try {
                            val insertedRow = saveCatFactUseCase.saveCatFact(factResult.factModel)
                            if (insertedRow > 0) {
                                _syncDataStatus.value = true
                            }
                        } catch (e: Exception) {
                            _syncDataStatus.value = false
                        }
                    }
                }

                is FactModelResult.Error -> {
                    //                    _factStateFlow.value = factResult.factModel
                }
            }
        }
    }
}

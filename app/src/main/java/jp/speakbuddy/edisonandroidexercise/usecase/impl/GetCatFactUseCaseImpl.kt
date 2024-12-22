package jp.speakbuddy.edisonandroidexercise.usecase.impl

import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModelResult
import jp.speakbuddy.edisonandroidexercise.usecase.GetCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelResult
import jp.speakbuddy.edisonandroidexercise.usecase.model.LengthInfo
import javax.inject.Inject

class GetCatFactUseCaseImpl @Inject constructor(
    val factRepository: FactRepository
): GetCatFactUseCase {
    override suspend fun getNewFact(): FactModelResult {
        val result = factRepository.getCloudFact()
        return validateResult(result)
    }

    override suspend fun getCachedFact(): FactModelResult {
        val result = factRepository.getCachedFact()
        return validateResult(result)
    }

    private fun validateResult(result: FactDataModelResult): FactModelResult {
        when (result) {
            is FactDataModelResult.Success -> {
                return FactModelResult.Success(
                    factModel = FactModel(
                        fact = result.factDataModel.fact,
                        lengthInfo = LengthInfo(
                            length = result.factDataModel.length,
                            shouldShowLength = result.factDataModel.fact.length > 100
                        ),
                        multipleCats = result.factDataModel.fact.contains("cats")
                    )
                )
            }

            is FactDataModelResult.Error -> {
                return FactModelResult.Error(
                    errMessage = "${result.errMessage}. Datasource: ${result.dataSourceType}"
                )
            }
        }
    }
}
package jp.speakbuddy.edisonandroidexercise.usecase.impl

import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModel
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataSourceType
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactType
import jp.speakbuddy.edisonandroidexercise.usecase.SaveCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel
import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelViewDataSource
import javax.inject.Inject

class SaveCatFactUseCaseImpl @Inject constructor(
    val factRepository: FactRepository
): SaveCatFactUseCase {
    override suspend fun saveCatFact(factModel: FactModel): Long {
        val insertedRow = factRepository.saveFact(
            FactDataModel(
                fact = factModel.fact,
                length = factModel.lengthInfo.length,
                type = FactType.CatFact
            )
        )
        return insertedRow
    }

}
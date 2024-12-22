package jp.speakbuddy.edisonandroidexercise.usecase

import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModel

interface SaveCatFactUseCase {
    suspend fun saveCatFact(factModel: FactModel): Long
}
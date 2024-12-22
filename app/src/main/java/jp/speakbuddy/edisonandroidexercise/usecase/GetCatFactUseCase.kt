package jp.speakbuddy.edisonandroidexercise.usecase

import jp.speakbuddy.edisonandroidexercise.usecase.model.FactModelResult

interface GetCatFactUseCase {
    suspend fun getNewFact(): FactModelResult
    suspend fun getCachedFact(): FactModelResult
}
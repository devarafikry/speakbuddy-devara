package jp.speakbuddy.edisonandroidexercise.data.repository

import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModel
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModelResult

interface FactRepository {
    suspend fun getCachedFact(): FactDataModelResult
    suspend fun getCloudFact(): FactDataModelResult
    suspend fun saveFact(factDataModel: FactDataModel): Long
}
package jp.speakbuddy.edisonandroidexercise.data.datasource.network

import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactResponse

interface CloudFactDataSource {
    suspend fun getNetworkFactData(): FactResponse
    suspend fun getHostName(): String
}

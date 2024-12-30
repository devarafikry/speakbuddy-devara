package jp.speakbuddy.edisonandroidexercise.data.datasource.network

import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactResponse
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactService
import retrofit2.Retrofit
import javax.inject.Inject

class CloudFactDataSourceImpl @Inject constructor(
    val factService: FactService,
    val retrofit: Retrofit
): CloudFactDataSource {
    override suspend fun getNetworkFactData(): FactResponse {
        return factService.getFact()
    }

    override suspend fun getHostName(): String {
        return retrofit.baseUrl().host
    }
}



package jp.speakbuddy.edisonandroidexercise.data.repository

import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.CloudFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataSourceType
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModel
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactDataModelResult
import jp.speakbuddy.edisonandroidexercise.data.repository.model.FactType

class CatFactRepository(
    val cloudFactDataSource: CloudFactDataSource,
    val cachedCloudFactDataSource: DatabaseFactDataSource
    ): FactRepository {

    override suspend fun getCloudFact(): FactDataModelResult {
        try {
            val cloudModel = cloudFactDataSource.getNetworkFactData()
            return FactDataModelResult.Success(
                FactDataModel(
                    fact = cloudModel.fact,
                    type = FactType.CatFact,
                    datasource = getCloudDataSource(),
                    length = cloudModel.length,
                )
            )
        } catch (e: Exception) {
            return FactDataModelResult.Error(
                errMessage = e.message.toString(),
                dataSourceType = getCloudDataSource()
            )
        }

    }

    override suspend fun getCachedFact(): FactDataModelResult {
        try {
            val cachedModel = cachedCloudFactDataSource.getRoomFactData()
            if (cachedModel != null) {
                return FactDataModelResult.Success(
                    factDataModel = FactDataModel(
                        fact = cachedModel.fact,
                        type = FactType.CatFact,
                        length = cachedModel.length,
                        datasource = getCacheDataSource(
                            cachedModel.savedTimeMillis
                        )
                    )
                )
            } else {
                return FactDataModelResult.Empty
            }
        } catch (e: Exception) {
            return FactDataModelResult.Error(
                errMessage = e.message.toString(),
                dataSourceType = getCacheDataSource()
            )
        }
    }

    override suspend fun saveFact(factDataModel: FactDataModel): Long {
        return try {
            cachedCloudFactDataSource.insertFactData(
                FactEntity(
                    fact = factDataModel.fact,
                    length = factDataModel.length
                )
            )
        } catch (e: Exception) {
            return 0
        }
    }

    private fun getCacheDataSource(savedTimeMillis: Long = 0L) = FactDataSourceType.Database(
        lastUpdateMillis = savedTimeMillis
    )

    private suspend fun getCloudDataSource() = FactDataSourceType.Network(
        apiHost = cloudFactDataSource.getHostName()
    )
}
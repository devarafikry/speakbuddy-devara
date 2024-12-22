package jp.speakbuddy.edisonandroidexercise.data.datasource.database

import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDao
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity
import javax.inject.Inject

class DatabaseFactDataSourceImpl @Inject constructor(
    private val factDao: FactDao
) : DatabaseFactDataSource {

    override suspend fun getRoomFactData(): FactEntity? {
        return fetchDatabaseData().firstOrNull()
    }

    override suspend fun insertFactData(factEntity: FactEntity): Long {
        return factDao.insertFact(factEntity)
    }

    fun deleteAllFacts() {
        factDao.deleteAllRecords()
    }

    private fun fetchDatabaseData(): List<FactEntity> {
        return factDao.getAllFacts()
    }
}

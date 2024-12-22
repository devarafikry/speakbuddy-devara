package jp.speakbuddy.edisonandroidexercise.data.datasource.database

import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactEntity

interface DatabaseFactDataSource {
    suspend fun getRoomFactData(): FactEntity?
    suspend fun insertFactData(factEntity: FactEntity): Long
}

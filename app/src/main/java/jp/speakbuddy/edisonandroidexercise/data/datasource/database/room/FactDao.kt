package jp.speakbuddy.edisonandroidexercise.data.datasource.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FactDao {

    @Insert
    fun insertFact(fact: FactEntity): Long

    @Query("SELECT * FROM FactEntity")
    fun getAllFacts(): List<FactEntity>

    @Query("DELETE FROM FactEntity WHERE id = :id")
    fun deleteFactById(id: Long): Int

    @Query("DELETE FROM FactEntity")
    fun deleteAllRecords(): Int
}
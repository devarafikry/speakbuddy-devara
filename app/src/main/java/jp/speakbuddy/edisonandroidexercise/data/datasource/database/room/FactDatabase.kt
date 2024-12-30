package jp.speakbuddy.edisonandroidexercise.data.datasource.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}
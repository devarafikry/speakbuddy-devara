package jp.speakbuddy.edisonandroidexercise.data.datasource.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FactEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "fact") val fact: String,
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "saved_time_millis")
    val savedTimeMillis: Long = System.currentTimeMillis()
)
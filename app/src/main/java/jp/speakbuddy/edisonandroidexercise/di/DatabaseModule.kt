package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDao
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFactDatabase(@ApplicationContext context: Context): FactDatabase {
        return Room.databaseBuilder(
            context,
            FactDatabase::class.java,
            "fact_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFactDao(factDatabase: FactDatabase): FactDao {
        return factDatabase.factDao()
    }
}
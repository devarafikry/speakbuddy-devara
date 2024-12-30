package jp.speakbuddy.edisonandroidexercise.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.DatabaseFactDataSourceImpl
import jp.speakbuddy.edisonandroidexercise.data.datasource.database.room.FactDao
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.CloudFactDataSource
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.CloudFactDataSourceImpl
import jp.speakbuddy.edisonandroidexercise.data.datasource.network.service.FactService
import jp.speakbuddy.edisonandroidexercise.data.repository.CatFactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.dispatcher.DefaultDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.dispatcher.DispatcherProvider
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCatFactRepository(
        cloudFactDataSource: CloudFactDataSource,
        databaseFactDataSource: DatabaseFactDataSource
    ): FactRepository {
        return CatFactRepository(
            cloudFactDataSource = cloudFactDataSource,
            cachedCloudFactDataSource = databaseFactDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCloudFactDataSource(
        factService: FactService,
        retrofit: Retrofit
    ): CloudFactDataSource {
        return CloudFactDataSourceImpl(
            factService = factService,
            retrofit = retrofit
        )
    }

    @Provides
    @Singleton
    fun provideDatabaseFactDataSource(
        factDao: FactDao
    ): DatabaseFactDataSource {
        return DatabaseFactDataSourceImpl(
            factDao = factDao
        )
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}
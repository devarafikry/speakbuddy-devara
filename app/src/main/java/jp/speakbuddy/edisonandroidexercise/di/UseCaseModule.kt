package jp.speakbuddy.edisonandroidexercise.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.usecase.GetCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.SaveCatFactUseCase
import jp.speakbuddy.edisonandroidexercise.usecase.impl.GetCatFactUseCaseImpl
import jp.speakbuddy.edisonandroidexercise.usecase.impl.SaveCatFactUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetCatFactUseCase(factRepository: FactRepository): GetCatFactUseCase {
        return GetCatFactUseCaseImpl(
            factRepository = factRepository
        )
    }

    @Singleton
    @Provides
    fun provideSaveCatFactUseCase(factRepository: FactRepository): SaveCatFactUseCase {
        return SaveCatFactUseCaseImpl(
            factRepository = factRepository
        )
    }
}
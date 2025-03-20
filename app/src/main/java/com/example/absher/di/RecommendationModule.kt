package com.example.absher.di

import com.example.absher.services.adapter.HttpExceptionHandler
import com.example.absher.services.adapter.RecommendationApiAdapter
import com.example.absher.services.data.datasource.RemoteRecommendationMeetingDataSource
import com.example.absher.services.domain.repository.RecommendationRepository
import com.example.absher.services.domain.usecases.GetRecommendationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecommendationModule {

    @Provides
    @Singleton
    fun provideRecommendationApiAdapter(
        httpExceptionHandler: HttpExceptionHandler,
        okHttpClient: OkHttpClient
    ): RecommendationApiAdapter {
        return RecommendationApiAdapter(httpExceptionHandler, okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRecommendationRepository(
        remoteDataSource: RemoteRecommendationMeetingDataSource
    ): RecommendationRepository {
        return RecommendationRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetRecommendationUseCase(repository: RecommendationRepository): GetRecommendationUseCase {
        return GetRecommendationUseCase(repository)
    }
} 